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

                setText(data.hPointersText, 'hPointersText');
                createMatrixTable(data.pointers, 'pointersTable');
                createMatrixTableGlobal(data.symbolicMatrix, 'symbolicMatrixTable');
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

function createMatrixTable(matrixArray, className) {
    let table = document.createElement('table');
    table.className = "table text-center";
    table.style.cssText = 'color: #333; border-right:1px solid #333; border-left:1px solid #333; border-collapse: initial; border-radius:25px;';

    for (let i = 0; i < matrixArray.length; i++) {
        let row = table.insertRow();
        let cell1 = row.insertCell(0);
        let cell2 = row.insertCell(1);
        let cell3 = row.insertCell(2);

        cell1.innerHTML = matrixArray[i].coefficient;
        cell2.innerHTML = matrixArray[i].hCoefficient;
        cell3.innerHTML = matrixArray[i].yCoefficient;
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
