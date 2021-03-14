if(localStorage.getItem('simulations')== null){
    console.log("session is empty");
}else{
    let obj = JSON.parse(localStorage.getItem("simulations"));
    let infections = jsonInfectionsToSeries(obj);
    renderChart(infections, 'infectionChart', 'Infections');
    let healthyPeople = jsonHealthyPeopleToSeries(obj);
    renderChart(healthyPeople, 'healthyPeopleChart', 'Healthy people who can be infected');
    let deaths = jsonDeathsToSeries(obj);
    renderChart(deaths, 'deathsChart', 'Deaths');
    let recovered = jsonRecoveredToSeries(obj);
    renderChart(recovered, 'recoveredChart', 'Recovered');
}

function renderChart(series, divId, titleLabel) {
    let chart = JSC.Chart(divId, { 
      debug: true, 
      type: 'line', 
      title_label_text: titleLabel, 
      legend_visible: false, 
      annotations: [ 
        { 
          label_text: 'Data Accuracy: +/- 5', 
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
