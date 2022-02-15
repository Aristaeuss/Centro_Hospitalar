const popup = document.querySelector('.popup');
console.log(popup);

function togglePopup() {
    popup.classList.toggle('hidden');
}



const popup1 = document.querySelector('.popup1');
console.log(popup1);

function togglePopup1() {
    popup1.classList.toggle('hidden');
}

const popup2 = document.querySelector('.popup2');
console.log(popup2);

function togglePopup2() {
    popup2.classList.toggle('hidden');
}

const popup3 = document.querySelector('.popup3');
console.log(popup3);

function togglePopup3() {
    popup3.classList.toggle('hidden');
}

//Get the button:
mybutton = document.getElementById("info-button");

// When the user scrolls down 20px from the top of the document, show the button
window.onscroll = function() {scrollFunction()};

function scrollFunction() {
  if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
    mybutton.style.display = "block";
  } else {
    mybutton.style.display = "none";
  }
}

// When the user clicks on the button, scroll to the top of the document
function topFunction() {
  document.body.scrollTop = 0; // For Safari
  document.documentElement.scrollTop = 0; // For Chrome, Firefox, IE and Opera
}