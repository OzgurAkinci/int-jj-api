package com.app.integraljjapi.controller;

import com.app.integraljjapi.api.EvalVisitor;
import com.app.integraljjapi.api.IntParser;
import com.app.integraljjapi.dto.*;
import com.app.integraljjapi.util.AppUtils;
import com.app.integraljjapi.util.FileUtils;
import com.app.integraljjapi.util.LatexUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Base64;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Controller
public class Api {

    @RequestMapping("/api")
    public ModelAndView api(ModelAndView mv) {
        mv.setViewName("api");
        return mv;
    }

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

    @PostMapping(value ="/actions/downloadPdfFile")
    public void downloadPdfFile(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestDTO requestDTO) throws Exception {
        var responseDTO = getNValue(requestDTO.getV());
        var latexContent = LatexUtils.createLatexFromData(responseDTO);

        String fileName = "latex";
        File temp = File.createTempFile(fileName, ".tex");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(temp))) {
            bw.write(latexContent);
        }

        String absolutePath = temp.getAbsolutePath();
        String tempFilePath = absolutePath
                .substring(0, absolutePath.lastIndexOf(File.separator));

        try{
            //https://miktex.org/download
            ProcessBuilder builder = new ProcessBuilder("pdflateX", absolutePath);
            builder.directory(new File(tempFilePath));
            builder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
            builder.redirectErrorStream(true);
            Process process = builder.start();
            process.waitFor(5, TimeUnit.SECONDS);

            //pdflatex ile generate edilen pdf dosyası indiriliyor.
            Path path = Paths.get(tempFilePath + "/" + temp.getName().replace(".tex", ".pdf"));
            if(FileUtils.isFileExist(path)) {
                Path fileStorageLocation = path.toAbsolutePath().normalize();
                Resource resource = new UrlResource(fileStorageLocation.toUri());

                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".pdf");
                response.getOutputStream().write(resource.getContentAsByteArray());
                response.flushBuffer();
            }else {
                throw new Exception("Getting error while creating pdf file.");
            }
        }catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @PostMapping(value ="/actions/downloadLatexFile")
    public void downloadLatexFile(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestDTO requestDTO) throws Exception {
        var responseDTO = getNValue(requestDTO.getV());
        var latexContent = LatexUtils.createLatexFromData(responseDTO);
        String base64String = FileUtils.createTexFileAndConvertToBase64(latexContent);

        response.setContentType("text/x-tex");
        response.setHeader("Content-Disposition", "attachment; filename=latex.tex");
        response.getOutputStream().write(Base64.getDecoder().decode(base64String));
        response.flushBuffer();
    }

    private ResponseDTO getNValue(String v) throws Exception {
        try {
            ResponseDTO responseDTO = new ResponseDTO();

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
