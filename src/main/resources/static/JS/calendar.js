window.daysThis;
window.daysNext;
window.speciality;
window.scheduledAppointmentToCancelId;
window.doctor;

/* Script para o popup do filtro */

const popup = document.querySelector('.popup');

function togglePopup() {
    popup.classList.toggle('hidden');
};

/* Script para o calendar dynamics */

const date = new Date();


const NUM_DAYS_PER_WEEK = 7;
const FIRST_COLUMN_WEEKDAY_NUMBER = 7;
const NUM_CALENDAR_ROWS = 6;
const NUM_MONTHS = 12;
const TODAYS_DATE = new Date();
const MONTH_NAMES = [
    "Janeiro",
    "Fevereiro",
    "Março",
    "Abril",
    "Maio",
    "Junho",
    "Julho",
    "Agosto",
    "Setembro",
    "Outubro",
    "Novembro",
    "Dezembro",
];
var displayedMonth = TODAYS_DATE.getMonth();

const renderCalendar = () => {
    const lastDateOfThisMonth = new Date(
        TODAYS_DATE.getFullYear(),
        displayedMonth + 1,
        0
    );

    const lastDayOfThisMonth = lastDateOfThisMonth.getDate();

    const lastDateOfPrevMonth = new Date(
        TODAYS_DATE.getFullYear(),
        displayedMonth,
        0
    );

    const lastDayOfPrevMonth = lastDateOfPrevMonth.getDate();

    const numDaysOfPrevMonthToDisplay = ((lastDateOfPrevMonth.getDay() + 1) - (FIRST_COLUMN_WEEKDAY_NUMBER - NUM_DAYS_PER_WEEK)) % NUM_DAYS_PER_WEEK;

    var numDaysOfNextMonthToDisplay = ((FIRST_COLUMN_WEEKDAY_NUMBER + NUM_DAYS_PER_WEEK) - (lastDateOfThisMonth.getDay() + 1)) % NUM_DAYS_PER_WEEK;

    if (numDaysOfPrevMonthToDisplay + lastDayOfThisMonth + numDaysOfNextMonthToDisplay < NUM_CALENDAR_ROWS * NUM_DAYS_PER_WEEK) {
        numDaysOfNextMonthToDisplay += NUM_DAYS_PER_WEEK;
    }

    let dayDivs = "";

    for (let i = 1; i <= numDaysOfPrevMonthToDisplay; i++) {
        dayDivs += `<div class="prev-date">${lastDayOfPrevMonth - numDaysOfPrevMonthToDisplay + i}</div>`;
    };

    for (let i = 1; i <= lastDayOfThisMonth; i++) {
        var divClass = null;
        if (i === TODAYS_DATE.getDate() && displayedMonth === TODAYS_DATE.getMonth()) {
            divClass = "today";
        } else if (displayedMonth === TODAYS_DATE.getMonth()) {
            if (!Object.values(daysThis).includes(0)) {
                if (Object.values(daysThis).includes(i)) {
                    divClass = "free";
                } else {
                    divClass = "occupied";
                }
            }
        } else if (displayedMonth === TODAYS_DATE.getMonth() + 1) {
            if (!Object.values(daysThis).includes(0)) {
                if (Object.values(daysNext).includes(i)) {
                    divClass = "free";
                } else {
                    divClass = "occupied";
                }
            }
        }
        if (divClass === null) {
            dayDivs += `<div>${i}</div>`
        } else {
            dayDivs += `<div class=${divClass}>${i}</div>`
        }
    }

    for (let i = 1; i <= numDaysOfNextMonthToDisplay; i++) {
        dayDivs += `<div class="next-date">${i}</div>`;
    }

    document.querySelector(".days").innerHTML = dayDivs;
    document.querySelector(".date h1").innerHTML = MONTH_NAMES[displayedMonth];
    document.querySelector(".date p").innerHTML = TODAYS_DATE.toLocaleDateString();
};

document.querySelector(".prev").addEventListener("click", () => {
    displayedMonth -= 1;
    if (displayedMonth === -1) {
        displayedMonth = 11;
    }
    renderCalendar();
});

document.querySelector(".next").addEventListener("click", () => {
    displayedMonth += 1;
    if (displayedMonth === 12) {
        displayedMonth = 0;
    }
    renderCalendar();
});

renderCalendar();


const fixedUrl = "/calendarioUtente/";
const fixedUrlForRescheduling = "/remarcarConsulta/";
$(document).on('click', '.days', function(event) {
    var url = fixedUrl;
    if (scheduledAppointmentToCancelId != "") {
        url = fixedUrlForRescheduling + scheduledAppointmentToCancelId + "/";
    }
    var text = $(event.target).text();
    var day = parseInt(text);
    if (displayedMonth === TODAYS_DATE.getMonth()) {
        if (Object.values(daysThis).includes(day)) {
            text += ",";
            text += TODAYS_DATE.getMonth() + 1;
            text += ",";
            text += TODAYS_DATE.getFullYear();
            url += text + "," + speciality;
            if (doctor != ""){
            url+=","+doctor}
            window.location.href = url;
        }
    } else if (displayedMonth === (TODAYS_DATE.getMonth() + 1) || displayedMonth === (TODAYS_DATE.getMonth() - 11)) {
        if (Object.values(daysNext).includes(day)) {
            text += ",";
            text += (TODAYS_DATE.getMonth() + 2);
            text += ",";
            var year = TODAYS_DATE.getFullYear();
            if (displayedMonth == 11) {
                year++;
            }
            text += year;
            url += text + "," + speciality;
            if (doctor != ""){
            url+=","+doctor}
            window.location.href = url;
        }
    }
});