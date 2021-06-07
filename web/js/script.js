/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function checkDate() {
    var dateFrom = document.getElementById("txt-dateFrom").value;
    var dateTo = document.getElementById("txt-dateTo").value;
    var valid = true;
    if(dateTo < dateFrom) {
        valid = false;
        document.getElementById("invalid-date").innerHTML = "Date To Must Be Later Than Date From";
    }
    return valid;
}

function resetDate() {
    var dateFrom = document.getElementById("txt-dateFrom").value;
    var dateTo = document.getElementById("txt-dateTo").value;
    
    if(dateFrom.length >= 1 || dateTo.length >= 1) {
        document.getElementById("txt-dateFrom").value = "";
        document.getElementById("txt-dateTo").value = "";
    } 
}

