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
        String date = "\\date{\\today}";
        String makeTitle = "\\maketitle";

        String section1 = "\\section{x < - > h dönüşümü yapılır.}";

        String documentContent = "Kullanıcıdan alınan n = "+ response.getN() +" değerine karşılık ilgili polinomlar oluşturulur." + "\n";

        String polyRow = "$f(x) = "+response.getPolynomialFunctionText()+"$" + "\n";
        String polyIntRow = "$f(x) = "+response.getPolynomialIntFunctionText()+"$" + "\n";

        String hyRowTitle = "Koordinat düzlemi üzerinde yer alacak h ve y değerleri belirlenir." + "\n";
        String hRow = "$h = "+response.gethPointersText()+"$" + "\n";
        String yRow = "$y = "+response.getyPointersText()+"$" + "\n";

        String xToHTitle = "x < - > h dönüşümü yapılır." + "\n";
        StringBuilder xToHText = new StringBuilder();
        for(var i=0; i<response.gethPointers().size(); i++) {
            var pointer = '('+response.gethPointers().get(i)+')';
            xToHText.append("$x< - >").append(response.gethPointers().get(i)).append("$").append("\n");
            xToHText.append("$").append(response.getPolynomialFunctionText().replaceAll("x", pointer)).append("$").append("\n");
        }


        var r =  documentClass + "\n"
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
                + date + "\n"
                + makeTitle + "\n"
                + section1 + "\n"
                + documentContent + "\n"
                + polyRow + "\n"
                + polyIntRow + "\n"
                + hyRowTitle + "\n"
                + hRow + "\n"
                + yRow + "\n"
                + xToHTitle + "\n"
                + xToHText + "\n"
                + endDocument;
        return r;
    }

}
