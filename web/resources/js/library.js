function checkValue(form, message) {
    
    var userInput = form[form.id + ":userName"];
    
    if (userInput.value == ''){
        alert(message);
        userInput.focus();
        return false;
    } 
    
    return true;
}

function showError(error) {
    alert(error);
}

