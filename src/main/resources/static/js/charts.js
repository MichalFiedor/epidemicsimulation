

let actualDate = new Date();
let hourFromStorage = localStorage.getItem('hour');
let minuteFromStorage = localStorage.getItem('minute');
if(actualDate.getHours()-hourFromStorage!=0){
    localStorage.clear();
}else{
    if(Math.abs(actualDate.getMinutes()-minuteFromStorage)>10){
        localStorage.clear();
    }
}

let simulations = localStorage.getItem('simulations');
let simulationsAsAJson = JSON.parse(simulations);

if(simulations== null){
    console.log("session is empty");
}else{
    let infections = jsonInfectionsToSeries(simulationsAsAJson);
    renderChart(infections, 'infectionChart', 'Infections');
    let healthyPeople = jsonHealthyPeopleToSeries(simulationsAsAJson);
    renderChart(healthyPeople, 'healthyPeopleChart', 'Healthy people who can be infected');
    let deaths = jsonDeathsToSeries(simulationsAsAJson);
    renderChart(deaths, 'deathsChart', 'Deaths');
    let recovered = jsonRecoveredToSeries(simulationsAsAJson);
    renderChart(recovered, 'recoveredChart', 'Recovered');
}

buildTable();

function buildTable(){
    var counter = localStorage.getItem('dayCounter');
    var tbody = $('#simulation-data tbody');
    for(var i=0; i<simulationsAsAJson.length;i++){
        var row = `<tr>
                  <td>${i+1}</td>
                  <td>${simulationsAsAJson[i].numberOfInfectedPeople}</td>
                  <td>${simulationsAsAJson[i].numberOfHealthyPeopleWhoCanBeInfected}</td>
                  <td>${simulationsAsAJson[i].numberOfDeathPeople}</td>
                  <td>${simulationsAsAJson[i].numberOfPeopleWhoRecoveredAndGainedImmunity}</td>
                  `
                  tbody.append(row);
    }
}


let inputValues = localStorage.getItem('values').toString().split(',');

let tbodyInitialData = document.querySelector('tbody');
let trForInitialData = document.createElement('tr');
tbodyInitialData.appendChild(trForInitialData);

for(let i=0; i<inputValues.length-1; i++){
    let newDataToTable = document.createElement('td');
    newDataToTable.innerText=inputValues[i];
    trForInitialData.appendChild(newDataToTable);
}


let hideShowInitialTableBtn = document.getElementById('initial-data-btn');
let initialDataTable = document.getElementById("initial-data");
hideShowInitialTableBtn.addEventListener("click", function(){
    if(initialDataTable.style.display==="none"){
        hideShowInitialTableBtn.innerHTML="Hide Initial Data";
        initialDataTable.style.display="block";
    } else{
        hideShowInitialTableBtn.innerHTML="Show Initial Data";
        initialDataTable.style.display="none";
    }
})


let hideShowSimulationsTableBtn = document.getElementById('simulation-data-btn');
let simulationsDataTable = document.getElementById("simulation-data");
hideShowSimulationsTableBtn.addEventListener("click", function(){
    if(simulationsDataTable.style.display==="none"){
        hideShowSimulationsTableBtn.innerHTML="Hide Simulation Data";
        simulationsDataTable.style.display="block";
    } else{
        hideShowSimulationsTableBtn.innerHTML="Show Simulation Data";
        simulationsDataTable.style.display="none";
    }
})

function renderChart(series, divId, titleLabel) {
    let chart = JSC.Chart(divId, { 
      debug: true, 
      type: 'line', 
      legend_visible: false,
      xAxis_label_text: "Day",
      yAxis_label_text: titleLabel,
      annotations: [
        {
          position: 'inside top left',
          margin: 5
        }
      ], 
      series: series 
    });
    return chart;
    }

function jsonInfectionsToSeries(jsonObject){
    let infectedPeople = [];
    let iteratorForX = 1;

    for(let i=0; i<jsonObject.length; i++){
        infectedPeople.push({x: iteratorForX, y: jsonObject[i].numberOfInfectedPeople});
        iteratorForX++;
    }
    return [{name: 'Infected People', points: infectedPeople}];
}

function jsonHealthyPeopleToSeries(jsonObject){
    let healthyPeople = [];
    let iteratorForX = 1;
    for(let i=0; i<jsonObject.length; i++){
        healthyPeople.push({x: iteratorForX, y: jsonObject[i].numberOfHealthyPeopleWhoCanBeInfected});
        iteratorForX++;
    }
    return [{name: 'Healthy people who can be infected', points: healthyPeople}];
}

function jsonDeathsToSeries(jsonObject){
    let deaths = [];
    let iteratorForX = 1;
    for(let i=0; i<jsonObject.length; i++){
        deaths.push({x: iteratorForX, y: jsonObject[i].numberOfDeathPeople});
         iteratorForX++;
    }
    return [{name: 'Deaths', points: deaths}];
}

function jsonRecoveredToSeries(jsonObject){
    let recovered = [];
     let iteratorForX = 1;
    for(let i=0; i<jsonObject.length; i++){
        recovered.push({x: iteratorForX, y: jsonObject[i].numberOfPeopleWhoRecoveredAndGainedImmunity});
                 iteratorForX++;

    }
    return [{name: 'Recovered', points: recovered}];
}

