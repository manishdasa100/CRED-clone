export function formatCreditCardNnumber(cardNumber, delimeter) {
    console.log(cardNumber);
    console.log("Formating with " + delimeter);
    return(
        cardNumber.substring(0,4) + delimeter + 
        cardNumber.substring(4,8) + delimeter + 
        cardNumber.substring(8,12) + delimeter +
        cardNumber.substring(12,16)
    );
}
