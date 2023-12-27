package com.app.integraljjapi.util;

import com.app.integraljjapi.dto.ResponseDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class LatexUtils {

    public LatexUtils() {}

    public static String createLatexFromData(ResponseDTO response) throws IOException {
        String texContent = getResourceFileAsString("init.tex");

        texContent = "\\documentclass{article}\n" +
                "\\usepackage{amsmath}\n" +
                "\\usepackage{amssymb}\n" +
                "\\usepackage{cancel}\n" +
                "\\usepackage{setspace}\n" +
                "\\usepackage{graphicx}\n" +
                "\\usepackage{enumitem}\n" +
                "\\usepackage[english]{babel}\n" +
                "\\usepackage[letterpaper,top=2cm,bottom=2cm,left=1cm,right=2cm,marginparwidth=1 cm]{geometry}\n" +
                "\\usepackage{tikz}\n" +
                "\n" +
                "\\onehalfspacing\n" +
                "\\begin{document}\n" +
                "\\title{Simgesel Yaklaşımlarla Sayısal İntegral İfadelerinin Türetilmesi}\n" +
                "\\date{\\today}\n" +
                "\\author{\\@AUTHOR}\n" +
                "\\maketitle\n" +
                "\n" +
                "\\section{Kullanıcıdan alınan n = 2 değerine karşılık ilgili polinomlar oluşturulur.}\n" +
                "$f(x) = c_{2}x^2 + c_{1}x + c_{0}$\\\\\n" +
                "$f(x) = (\\frac{c_{2}x^3}{3}) + (\\frac{c_{1}x^2}{2}) + ((c_{0})x)$\n" +
                "\n" +
                "\\section{Merkeze taşınmış koodinat düzlemi}\n" +
                "\\begin{center}\n" +
                "\\begin{tikzpicture}\n" +
                "    % Koordinat eksenlerini çiz\n" +
                "    \\draw[line width=1mm,->] (-3,0) -- (3,0) node[right] {$x$}; % x eksen\n" +
                "    \\draw[line width=1mm,->] (0,-3) -- (0,3) node[above] {$y$}; % y eksen\n" +
                "\n" +
                "    % x eksenindeki noktalar\n" +
                "    \\draw[fill=black] (-2,0) circle (0.1) node[below] {$-h$};\n" +
                "    \\draw[fill=black] (0,0) circle (0.1) node[below] {$0$};\n" +
                "    \\draw[fill=black] (2,0) circle (0.1) node[below] {$h$};\n" +
                "\n" +
                "    % y eksenindeki noktalar\n" +
                "    \\draw[fill=black] (-2,1) circle (0.1) node[left] {$y_0$};\n" +
                "    \\draw[fill=black] (0,2) circle (0.1) node[left] {$y_1$};\n" +
                "    \\draw[fill=black] (2,3) circle (0.1) node[left] {$y_2$};\n" +
                "\n" +
                "    % Parabolü çiz\n" +
                "    \\draw[domain=-2:2, smooth, variable=\\x, blue, thick] plot ({\\x}, {\\x*\\x + 2});\n" +
                "\\end{tikzpicture}\n" +
                "\\end{center}\n" +
                "\n" +
                "\\section{Koordinat düzlemi üzerinde yer alacak h ve y değerleri belirlenir.}\n" +
                "$h = -h,0,h$\\\\\n" +
                "$y = y_{-1},y_{0},y_{1}$\n" +
                "\n" +
                "\\section{Polinomlar için x - h dönüşü yapılır.}\n" +
                "$x \\leftrightarrow -h \\longrightarrow c_{2}(-h)^2 + c_{1}(-h) + c_{0} $\\\\\n" +
                "$x \\leftrightarrow 0 \\longrightarrow c_{2}(0)^2 + c_{1}(0) + c_{0}$\\\\\n" +
                "$x \\leftrightarrow h \\longrightarrow c_{2}(h)^2 + c_{1}(h) + c_{0}$\n" +
                "\n" +
                "\n" +
                "\\section{Katsayılardan oluşan sembolik matris oluşturulur.}\n" +
                "\\begin{center}\n" +
                "$\\begin{bmatrix}\n" +
                "h^2&-h&1&y_{-1}\\\\\n" +
                "0&0&1&y_{0}\\\\\n" +
                "h^2&h&1&y_{1}\\\\\n" +
                "\\end{bmatrix} $ \n" +
                "\\end{center}\n" +
                "\n" +
                "\\section{İndirgenmiş eşelon matrisin hesaplanmasında kullanılacak algoritma belirlenir.}\n" +
                "Adım 1. m × n matrisi A ile başlayın. Eğer A = 0 ise, Adım 7'ye geçin.  \\\\\n" +
                "Adım 2. En sola yerleşmiş sıfır olmayan bir sütunu belirleyin. \\\\\n" +
                "Adım 3. Bu sütunun en üst pozisyonuna (bu pozisyonu pivot pozisyon olarak adlandırıyoruz) bir yerine getirmek için elementer satır işlemlerini kullanın.  \\\\\n" +
                "Adım 4. Pivot pozisyonun altına (kesinlikle) sıfır yerleştirmek için elementer satır işlemlerini kullanın.  \\\\\n" +
                "Adım 5. Pivot pozisyonunun altında (kesinlikle) sıfır olan başka satırlar yoksa, Adım 7'ye gidin.  \\\\\n" +
                "Adım 6. Pivot pozisyonunun altındaki satırlardan oluşan alt matrise Adım 2-5'i uygulayın.  \\\\\n" +
                "Adım 7. Elde edilen matris satır basamak formundadır.  \\\\\n" +
                "Daha fazla işlem yapmak için aşağıdaki adımları takip edebilirsiniz, Satır Basamak Formunu Azaltma  \\\\\n" +
                "Adım 8. Adım 7'de elde edilen satır basamak formundaki tüm öncül birleri belirleyin.  \\\\\n" +
                "Adım 9. Adım 8'deki öncül bir içeren en sağdaki sütunu belirleyin (bu sütunu pivot sütunu olarak adlandırıyoruz).  \\\\\n" +
                "Adım 10. Pivot sütunundaki öncül birin üzerindeki tüm sıfır olmayan girişleri silmek için tip III elementer satır işlemlerini kullanın.  \\\\\n" +
                "Adım 11. Pivot sütununun solunda başka öncül bir içeren sütun kalmadıysa, Adım 13'e gidin.  \\\\\n" +
                "Adım 12. Pivot sütunun solundaki sütunlardan oluşan alt matrise Adım 9-11'i uygulayın.  \\\\\n" +
                "Adım 13. Elde edilen matris azaltılmış satır basamak formundadır.  \\\\\n" +
                "\n" +
                "\\section{İndirgenmiş eşelon matrisin oluşturulması için katsayılardan oluşan başlangıç matrisi oluşturulur.}\n" +
                "\\begin{center}\n" +
                "$\\begin{bmatrix}\n" +
                "1&-1&1&y_{-1}\\\\\n" +
                "0&0&1&y_{0}\\\\\n" +
                "1&-1&1&y_{1}\\\\\n" +
                "\\end{bmatrix} $ \n" +
                "\\end{center}\n" +
                "\n" +
                "\\section{Adım adım indirgenmiş matris hesaplanır.}\n" +
                "\\begin{center}\n" +
                "$PivotRow= 0, Pivot: 0, (R3 \\leftarrow R3 + R1 * -1) $\\\\\n" +
                "$\\begin{bmatrix}\n" +
                "1&-1&1&y_{-1}\\\\\n" +
                "0&0&1&y_{0}\\\\\n" +
                "1&-1&1&y_{1}\\\\\n" +
                "\\end{bmatrix} $ \n" +
                "\\end{center}\n" +
                "\n" +
                "\\section{İndirgenmiş eşelon matris}\n" +
                "\\begin{center}\n" +
                "$\\begin{bmatrix}\n" +
                "1&0&0&((y_{-1}) + ((y_{1} - (y_{-1})) * 0.5)) - y_{0}\\\\\n" +
                "0&1&0&(y_{1} - (y_{-1})) * 0.5\\\\\n" +
                "0&-0&1&y_{0}\\\\\n" +
                "\\end{bmatrix} $ \n" +
                "\\end{center}\n" +
                "\n" +
                "\n" +
                "\\end{document}";
        return texContent;
    }

    static String getResourceFileAsString(String fileName) throws IOException {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        try (InputStream is = classLoader.getResourceAsStream(fileName)) {
            if (is == null) return null;
            try (InputStreamReader isr = new InputStreamReader(is); BufferedReader reader = new BufferedReader(isr)) {
                    return reader.lines().collect(Collectors.joining(System.lineSeparator()));
            }
        }
    }

}
