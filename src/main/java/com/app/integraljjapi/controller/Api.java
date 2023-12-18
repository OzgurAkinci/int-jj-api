package com.app.integraljjapi.controller;

import com.app.integraljjapi.api.EvalVisitor;
import com.app.integraljjapi.api.IntParser;
import com.app.integraljjapi.dto.RequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.ByteArrayInputStream;

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
        int n = getNValue(requestDTO.getV());

        return ResponseEntity.ok(n);
    }

    private int getNValue(String v) throws Exception {
        try {
            //File file = ResourceUtils.getFile("classpath:input.txt");
            //InputStream in = new FileInputStream(file);

            IntParser intParser = new IntParser(new ByteArrayInputStream(v.getBytes()));
            EvalVisitor ev = new EvalVisitor();
            Object obj = ev.visit(intParser.Start());

            return Integer.parseInt(obj.toString());
        }catch (Exception e) {
            throw new Exception("Hatalı giriş yapıldı. Lütfen tekrar deneyin.");
        }
    }
}
