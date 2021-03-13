let urlToGetAllSimulationForInitialDataId = 'http://localhost:8080/initialdata/1/simulations'

fetch(urlToGetAllSimulationForInitialDataId)
.then(resp=>{
    return resp.json();
})
.then(obj=>{
    let infections = jsonInfectionsToSeries(obj);
    renderChart(infections, 'infectionChart', 'Infections');
    let healthyPeople = jsonHealthyPeopleToSeries(obj);
    renderChart(healthyPeople, 'healthyPeopleChart', 'Healthy people who can be infected');
    let deaths = jsonDeathsToSeries(obj);
    renderChart(deaths, 'deathsChart', 'Deaths');
    let recovered = jsonRecoveredToSeries(obj);
    renderChart(recovered, 'recoveredChart', 'Recovered');
})
.catch(function(error){
    console.log(error);
});

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
    for(let i=0; i<jsonObject.length; i++){
        infectedPeople.push({x: jsonObject[i].id, y: jsonObject[i].numberOfInfectedPeople});
    }
    return [{name: 'Infected People', points: infectedPeople}];
}

function jsonHealthyPeopleToSeries(jsonObject){
    let healthyPeople = [];
    for(let i=0; i<jsonObject.length; i++){
        healthyPeople.push({x: jsonObject[i].id, y: jsonObject[i].numberOfHealthyPeopleWhoCanBeInfected});
    }
    return [{name: 'Healthy people who can be infected', points: healthyPeople}];
}

function jsonDeathsToSeries(jsonObject){
    let deaths = [];
    for(let i=0; i<jsonObject.length; i++){
        deaths.push({x: jsonObject[i].id, y: jsonObject[i].numberOfDeathPeople});
    }
    return [{name: 'Deaths', points: deaths}];
}

function jsonRecoveredToSeries(jsonObject){
    let recovered = [];
    for(let i=0; i<jsonObject.length; i++){
        recovered.push({x: jsonObject[i].id, y: jsonObject[i].numberOfPeopleWhoRecoveredAndGainedImmunity});
    }
    return [{name: 'Recovered', points: recovered}];
}