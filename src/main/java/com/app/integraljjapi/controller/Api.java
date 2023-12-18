package com.app.integraljjapi.controller;

import com.app.integraljjapi.api.EvalVisitor;
import com.app.integraljjapi.api.IntParser;
import com.app.integraljjapi.dto.PointerDTO;
import com.app.integraljjapi.dto.PolynomialDTO;
import com.app.integraljjapi.dto.RequestDTO;
import com.app.integraljjapi.dto.ResponseDTO;
import com.app.integraljjapi.util.AppUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.stream.Collectors;

@Controller
public class Api {

    @RequestMapping("/")
    public ModelAndView home(ModelAndView mv) {
        mv.addObject("param1", new RequestDTO());
        mv.setViewName("home");
        return mv;
    }

    @PostMapping(value = "/actions/postForm")
    public ResponseEntity<?> saveForm(@RequestBody RequestDTO requestDTO) throws Exception {
        ResponseDTO responseDTO = getNValue(requestDTO.getV());

        return ResponseEntity.ok(responseDTO);
    }

    private ResponseDTO getNValue(String v) throws Exception {
        try {
            ResponseDTO responseDTO = new ResponseDTO();
            //File file = ResourceUtils.getFile("classpath:input.txt");
            //InputStream in = new FileInputStream(file);

            IntParser intParser = new IntParser(new ByteArrayInputStream(v.getBytes()));
            EvalVisitor ev = new EvalVisitor();
            Object obj = ev.visit(intParser.Start());
            int n = Integer.parseInt(obj.toString());

            // pointers
            var pointers = AppUtils.calcPointers(n+1);
            var hPointers = Arrays.stream(pointers).toList().stream().map(PointerDTO::gethCoefficient).collect(Collectors.joining(","));
            var yPointers = Arrays.stream(pointers).toList().stream().map(PointerDTO::getyCoefficient).collect(Collectors.joining(","));

            // polynomial functions
            var polynomialDTO = AppUtils.getPolynomialDto(n);
            var polynomialFunction = polynomialDTO.getPoly();
            var polynomialIntFunction = polynomialDTO.getPolyInt();

            responseDTO.setN(n);
            responseDTO.setPointers(pointers);
            responseDTO.sethPointersText(hPointers);
            responseDTO.setyPointersText(yPointers);
            responseDTO.setPolynomialDTO(polynomialDTO);
            responseDTO.setPolynomialFunctionText(polynomialFunction);
            responseDTO.setPolynomialIntFunctionText(polynomialIntFunction);

            return responseDTO;
        }catch (Exception e) {
            throw new Exception("Hatalı giriş yapıldı. Lütfen tekrar deneyin.");
        }
    }
}
