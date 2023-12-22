package com.app.integraljjapi.controller;

import com.app.integraljjapi.api.EvalVisitor;
import com.app.integraljjapi.api.IntParser;
import com.app.integraljjapi.dto.*;
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

    @RequestMapping("/latex")
    public ModelAndView test(ModelAndView mv) {
        mv.setViewName("latex");
        return mv;
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
            var hPointersText = Arrays.stream(pointers).toList().stream().map(PointerDTO::gethCoefficient).collect(Collectors.joining(","));
            var hPointers = Arrays.stream(pointers).toList().stream().map(PointerDTO::gethCoefficient).toList();
            var yPointers = Arrays.stream(pointers).toList().stream().map(PointerDTO::getyCoefficient).collect(Collectors.joining(","));

            // polynomial functions
            var polynomialDTO = AppUtils.getPolynomialDto(n);
            var polynomialFunction = polynomialDTO.getPoly();
            var polynomialIntFunction = polynomialDTO.getPolyInt();

            // symbolic matrix
            var symbolicMatrix = AppUtils.initSymbolicMatrix(pointers, n);

            // init matrix
            var initMatrix = AppUtils.initMatrix(pointers, n);

            // matrix
            var matrix = new MatrixDTO();
            String[] B = new String[pointers.length];
            for(int i=0; i<pointers.length; i++) {B[i] = pointers[i].getyCoefficient();}
            matrix = AppUtils.findEchelonMatrix(initMatrix, B);

            responseDTO.setN(n);
            responseDTO.setPointers(pointers);
            responseDTO.sethPointersText(hPointersText);
            responseDTO.sethPointers(hPointers);
            responseDTO.setyPointersText(yPointers);
            responseDTO.setPolynomialDTO(polynomialDTO);
            responseDTO.setPolynomialFunctionText(polynomialFunction);
            responseDTO.setPolynomialIntFunctionText(polynomialIntFunction);
            responseDTO.setSymbolicMatrix(symbolicMatrix);
            responseDTO.setMatrixDTO(matrix);

            return responseDTO;
        }catch (Exception e) {
            throw new Exception("Hatalı giriş yapıldı. Lütfen tekrar deneyin.");
        }
    }
}
