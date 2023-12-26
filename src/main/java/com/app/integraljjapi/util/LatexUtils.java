package com.app.integraljjapi.util;

import com.app.integraljjapi.dto.ResponseDTO;

public class LatexUtils {

    public LatexUtils() {}

    public static String createLatexFromData(ResponseDTO response) {
        var latexContent = "\\documentclass{article}\n\\begin{document}\nHello, world!\n\\end{document}";



        return latexContent;
    }

}
