$(document).ready(function() {
    $(".results").hide();
    $('.errorDiv').hide();
    $("#btn").click(function(event) {
        event.preventDefault();
        let vd = document.getElementById("v");
        let formDataV = {
            'v': vd.value
        };
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/actions/postForm",
            data: JSON.stringify(formDataV),
            dataType: 'json',
            cache: false,
            timeout: 600000,
            success: function(data) {
                $('.results').show();
                $('#resultSolutionMatrix').html(JSON.stringify(data.matrixDTO.solutionMatrix, null, 2));
                setText('<b>p(x)=</b> ' + data.polynomialDTO.poly + '<br/>' + '<b>P(x)=</b> ' + data.polynomialDTO.polyInt, 'nPolyText');
                setText(data.polynomialDTO.polyInt, 'nPolyIntText');
                setText('<b>h -></b> ' + data.hPointersText + '<br/>' + '<b>y -></b> ' + data.yPointersText, 'hyPointersText');
                setXToHText(data, 'xToHText');
                createMatrixTable(data.symbolicMatrix, 'symbolicMatrixTable');
                createMatrixTable(data.matrixDTO.initMatrix, 'initMatrixTable');
                createMatrixTable(data.matrixDTO.echelonMatrix, 'echelonMatrixTable');
                createEchelonStepsMatrixTable(data.matrixDTO.steps, 'echelonStepsMatrixTable');
                setRowOperations(data.matrixDTO.steps,'rowOperations');
                $(".errorDiv").hide();
            },
            error: function(e) {
                $('#resultSolutionMatrix').html("");
                $('.results').hide();
                $('.errorDiv').html(JSON.parse(e.responseText).message);
                $(".errorDiv").show();
            }
        });
    });


});

function createLatexPdf() {
    let vd = document.getElementById("v");
    let formDataV = {
        'v': vd.value
    };

    $.ajax({
        url: '/actions/createLatexPdf',
        type: 'POST',
        contentType: "application/json",
        data: JSON.stringify(formDataV),
        xhrFields: {
            responseType: 'blob'
        },
        success: function(blob) {
            let downloadUrl = URL.createObjectURL(blob);
            let a = document.createElement('a');
            a.href = downloadUrl;
            a.download = 'latex.pdf';
            document.body.appendChild(a);
            a.click();
            document.body.removeChild(a);
            URL.revokeObjectURL(downloadUrl);
        },
        error: function(xhr, status, error) {
            console.error("Dosya indirilirken bir hata oluştu:", error);
        }
    });
}


function setRowOperations(steps, className) {
    let div = document.createElement('div');
    for(let s=0; s<steps.length; s++) {
        let p = document.createElement('p');
        p.append(steps[s].process);
        div.append(p);
    }
    $('.' + className).html(div);
}

function setText(text, className) {
    $('.' + className).html(text);
}
function setXToHText(data, className) {
    let responseTxt = "";
    for(let i=0; i<data.hPointers.length; i++) {
        let pointer = '('+data.hPointers[i]+')';
        responseTxt += '<b> x <-> '+ data.hPointers[i] + '</b><br/>'+ data.polynomialFunctionText.replaceAll('x', pointer) + "<br/>";
    }

    $('.' + className).html(responseTxt);
}

function createEchelonStepsMatrixTable(steps, className) {
    let tables = document.createElement('div');
    for(let s=0; s<steps.length; s++) {
        let table = document.createElement('table');
        table.className = "table text-center";
        table.style.cssText = 'color: #333; border-right:1px solid #333; border-left:1px solid #333; border-collapse: initial; border-radius:25px;';

        let matrixArray = steps[s].matrix;
        for (let i = 0; i < matrixArray.length; i++) {
            let rowData = matrixArray[i];
            let row = table.insertRow();
            for(let j=0; j<rowData.length; j++) {
                let cell = row.insertCell(j);
                cell.innerHTML = rowData[j];
            }
            //let cell = row.insertCell(rowData.length);
            //cell.innerHTML = steps[s].solution[i];
        }
        tables.append("PivotRow= " + steps[s].pivotRow + ", Pivot: " + steps[s].pivot + ", (" + steps[s].process + ")");
        tables.append(table);
    }
    $('.' + className).html(tables);
}

function createMatrixTable(matrixArray, className) {
    let table = document.createElement('table');
    table.className = "table text-center";
    table.style.cssText = 'color: #333; border-right:1px solid #333; border-left:1px solid #333; border-collapse: initial; border-radius:25px;';

    for (let i = 0; i < matrixArray.length; i++) {
        let rowData = matrixArray[i];
        let row = table.insertRow();
        for(let j=0; j<rowData.length; j++) {
            let cell = row.insertCell(j);
            cell.innerHTML = rowData[j];
        }
    }
    $('.' + className).html(table);
}

function createMatrixTableGlobal(matrixArray, className) {
    let table = document.createElement('table');
    table.className = "table text-center";
    table.style.cssText = 'color: #333; border-right:1px solid #333; border-left:1px solid #333; border-collapse: initial; border-radius:25px;';

    for (let i = 0; i < matrixArray.length; i++) {
        let colSize = matrixArray[i].length;
        let row = table.insertRow();
        let cell1 = row.insertCell(0);
        let cell2 = row.insertCell(1);
        let cell3 = row.insertCell(2);

        cell1.innerHTML = matrixArray[i];
        cell2.innerHTML = matrixArray[i];
        cell3.innerHTML = matrixArray[i];
    }
    $('.' + className).html(table);
}
