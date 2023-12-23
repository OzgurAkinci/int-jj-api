$(document).ready(function() {
    $(".results").hide();

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
                $('#error').html("");
                $('#resultSolutionMatrix').html(JSON.stringify(data.matrixDTO.solutionMatrix, null, 2));
                $('#resultAllData').html(JSON.stringify(data, null, 2));

                setText('<b>p(x)=</b> ' + data.polynomialDTO.poly + '<br/>' + '<b>P(x)=</b> ' + data.polynomialDTO.polyInt, 'nPolyText');
                setText(data.polynomialDTO.polyInt, 'nPolyIntText');
                setText('<b>h -></b> ' + data.hPointersText + '<br/>' + '<b>y -></b> ' + data.yPointersText, 'hyPointersText');
                setXToHText(data, 'xToHText');
                createSymbolicMatrixTable(data.symbolicMatrix, 'symbolicMatrixTable');
                createSymbolicMatrixTable(data.matrixDTO.initMatrix, 'initMatrixTable');
                //createMatrixTableGlobal(data.symbolicMatrix, 'symbolicMatrixTable');
                $(".results").show();
            },
            error: function(e) {
                $('#error').html(JSON.parse(e.responseText).message);
                $('#resultSolutionMatrix').html("");
                $('#resultAllData').html("");
                $(".results").show();
            }
        });
    });

});


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
function createSymbolicMatrixTable(matrixArray, className) {
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
