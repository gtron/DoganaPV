function gtfErrorField(field, msg) {
document.submitting = false ;
field.style.background = '#E44';
field.focus();
if(msg)alert(msg);
}
function gtfMinLen( field, min, nome, onBlur) {
if (onBlur || document.submitting) {
var v = field.value;
if ( v.length < i ){
document.submitting = false ;
gtfErrorField(field,'Il campo '+  nome + ' deve avere almeno ' + i + ' caratteri.' );
return false;
}}
return true ;
}

function gtfMaxLen(field, max) {   // il secondo parametro indica la lunghezza del carattere. Si potrebbe attivare onkeyup ... ora onblur
if ( field.value.length > max ) {
gtfErrorField(field, "Caratteri consentiti:" + numero + ". Caratteri inseriti:" + stringa.value.length + "."); 
return false;
}
return true ;
} 

function gtfNotNull( f ) {
if ( document.submitting ) {
var error = false;
if ( f.type && f.type == 'radio' && ! f.checked ) error = true ;
if ( ! f.value || f.value.lenght < 1 ) error = true ;
if ( error )
gtfErrorField(field,"Campo obbligatorio." );
return false;
}
return true ;
}		
		
function gtfOnSubmit(b) {try {
var f=b.form ;
if(!f)f=b ;
if(!f)f=document.forms[0] ;

for ( i in f.elements ) {

var elem = f.elements[i] ;
		
try {
if ( elem && elem.type && typeof(elem.type) == 'string' &&
(elem.type == 'text' || elem.type == 'checkbox' || elem.type == 'radio' || elem.type == 'password' || elem.type.match(/select/) ) && elem.onblur  && ( ! elem.disabled ) ) {
var b = elem.onblur() ;
if (! b) {document.submitting = false ;return false ;}
}
}
catch (e)  {
alert('Errore: ' + el + ' \n\n' + e.message);
document.submitting = false ;
}
}
return true ;
}
catch(e){ alert( e.message); return false ;}
}

function gtfOnlyDigit (stringa) {       // verifica che il tasto premuto sia relativo ad un numero.....generalmente onkeyup
if ( document.submitting ) {
var exp = /^([0-9])*$/;
var result = exp.test ( noBlanks(stringa.value) );
if ( !result) {
alert ( 'I valori devono essere numerici.' );
return false;
}
else {
return true ;
}
}
return true;
}


function gtfCheckRegexp(field, regexp) {
if ( document.submitting ) { 
var v = field.value;
v = v.replace(/^\s+|\s+$/g, '');
v = v.replace(/((\s*\S+)*)\s*/, "$1");
if ( v != '' ) return regexp.test(v);
return true ;
}}

function gtfCheckEmail(field) {
var exp = /^[_a-z0-9-]+(\.[_a-z0-9-]+)*@[a-z0-9-]+(\.[a-z0-9-]+)*(\.[a-z]{2,6})$/i ;
var b = gtfCheckRegexp( field, exp );
if ( ! b ) gtfErrorField(field,'Email non corretta' );
return b ;
}

function gtfCheckData(field) { 
var exp = /^([0-9]{2})([\/]{1})([0-9]{2})([\/]{1})([0-9]{4})$/;
var b = gtfCheckRegexp( field, exp );
if ( ! b ) gtfErrorField(field,'Email non corretta' );
return b ;
}
