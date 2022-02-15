para o file jsp
var list=${jsonDates};

para o file js
var list;
var listdates = JSON.parse(list);

para verificar se os dias estao livres
if(Object.values(listdates).includes(i)){
            days += `<div class="free">${i}</div>`;
          }

fetch(url)
        .then(res => res.json())
        .then(data => {
        data.map((slot) => {
                // create the elements
                let li = createNode('li'),
                    date = createNode('date'),
                    time = createNode('time'),
                    doctor= createNode('doctor');
                date.innerText = slot.date;
                time.innerText = slot.time;
                doctor.innerText=slot.doctor;
                // append all elements
                appendNode(li, date);
                appendNode(li, time);
                appendNode(li, doctor);
                appendNode(ul, li);
            });
            // code to handle the response
        }).catch(err => {
            console.error('Error: ', err);
        });


var specifiedElement1 = document.getElementById('popup1');
console.log(specifiedElement1);
document.addEventListener('click', function(event) {
  var isClickInside = specifiedElement1.contains(event.target);
  if (!isClickInside) {
    if(!hiddenOrNot1){
    togglePopup1();
    console.log("popup1 was false");
    hiddenOrNot1=true;
    console.log("popup1 was turned true");
    }
  }
});