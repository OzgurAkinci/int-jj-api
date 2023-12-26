package com.app.integraljjapi.util;

import com.app.integraljjapi.dto.ResponseDTO;

public class LatexUtils {

    public LatexUtils() {}

    public static String createLatexFromData(ResponseDTO response) {
        String documentClass = "\\documentclass{article}";
        String usePackageAmsMathH = "\\usepackage{amsmath}";
        String usePackageAmsSymb = "\\usepackage{amssymb}";
        String usePackageCancel = "\\usepackage{cancel}";
        String usePackageSetSpace = "\\usepackage{setspace}";
        String usePackageGraphicX = "\\usepackage{graphicx}";
        String usePackageEnumItem= "\\usepackage{enumitem}";
        String usePackageColors = "\\usepackage[colorlinks=true, allcolors=blue]{hyperref}";
        String usePackageBabel= "\\usepackage[english]{babel}";
        String usePackageGeometry= "\\usepackage[letterpaper,top=2cm,bottom=2cm,left=1cm,right=2cm,marginparwidth=1 cm]{geometry}";
        String oneHalfSpacing = "\\onehalfspacing";
        String beginDocument = "\\begin{document}";
        String endDocument = "\\end{document}";
        String title = "\\title{Simgesel Yaklaşımlarla Sayısal İntegral İfadelerinin Türetilmesi}";
        String author = "\\author{Yazar Adı}";
        String date = "\\date{\\today}";
        String makeTitle = "\\maketitle";
        String documentContent = "Bu bir LaTeX belgesidir. Buraya içerik ekleyebilirsiniz.";


        return documentClass + "\n"
                + usePackageAmsMathH + "\n"
                + usePackageAmsSymb + "\n"
                + usePackageCancel + "\n"
                + usePackageSetSpace + "\n"
                + usePackageGraphicX + "\n"
                + usePackageEnumItem + "\n"
                //+ usePackageColors + "\n"
                + usePackageBabel + "\n"
                + usePackageGeometry + "\n"
                + oneHalfSpacing + "\n"
                + beginDocument + "\n"
                + title + "\n"
                + author + "\n"
                + date + "\n"
                + makeTitle + "\n"
                + documentContent + "\n"
                + endDocument;
    }

}
