let form = document.getElementById("form");

async function submitForm(event, form){
  event.preventDefault();
  checkInputs();
  let smalls = getAllSmallsArray();
  if(smalls.some((small)=>small.classList.contains('error'))){
    checkInputs();
  }else{
    saveAllValuesFromInputsInLocalStorage();
    const data = new FormData(event.target);
    const formJSON = Object.fromEntries(data.entries());
    let response =  performPostHttpRequest('https://epidemicsimulationapp.herokuapp.com/initialdata/', formJSON);
    response.then((obj)=>{
    if(localStorage.length!=0){
      localStorage.clear
    }
      localStorage.setItem("simulations", JSON.stringify(obj))
      let storeDataTime = new Date();
      localStorage.setItem("hour", storeDataTime.getHours());
      localStorage.setItem("minute", storeDataTime.getMinutes());
      window.location.href="/charts"
    });
  }
}

form.addEventListener('submit', submitForm);

function saveAllValuesFromInputsInLocalStorage(){
  let dataFromInput = document.getElementsByClassName("border-customized-input");
  let inputValues = [];
  for(let i=0; i<dataFromInput.length; i++){
      inputValues.push(dataFromInput[i].value);
  }
  localStorage.setItem('values', inputValues);
}

async function performPostHttpRequest(fetchLink, body){
  if(!fetchLink || !body){
    throw new Error('One or more POST request parameters was not passed.');
  }
  try{
    let rawResponse = await fetch(fetchLink, {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(body, null, '\t')
    });

    return await rawResponse.json();
  }
  catch(err){
    console.error('Error at fetch POST: ${err}');
    throw err;
  }
}

function getAllSmallsArray(){
  let smalls = document.querySelectorAll('small');
  let smallsAsArray = Array.prototype.slice.call(smalls);
  return smallsAsArray;
}

function checkInputs(){
  let simulationNameValue = simulationName.value.trim();
  let populationSizeValue = populationSize.value.trim();
  let initialNumberOfInfectedValue = initialNumberOfInfected.value.trim();
  let numberOfPeopleWhoWillBeInfectedByOnePersonValue = numberOfPeopleWhoWillBeInfectedByOnePerson.value.trim();
  let mortalityRateValue = mortalityRate.value.trim();
  let daysFromInfectionToRecoveryeValue = daysFromInfectionToRecovery.value.trim();
  let daysFromInfectionToDeathValue = daysFromInfectionToDeath.value.trim();
  let numberOfSimulationDaysValue = numberOfSimulationDays.value.trim();

  let validData=true;

  if(simulationNameValue===''){
    setErrorFor(simulationName, 'Simulation name cannot be blank', validData);
  }else if(simulationNameValue.length>50){
    setErrorFor(simulationName, 'Simulation name cannot exceed 255 charts.')
  }else{
    setSuccessFor(simulationName, validData);
  }

  if(populationSizeValue===''){
    setErrorFor(populationSize, 'Population Size cannot be blank', validData);
  }else if(populationSizeValue<1000){
    setErrorFor(populationSize, 'Population Size must be greater than 1000', validData)
  }else if(!isInt(populationSizeValue)){
    setErrorFor(populationSize, 'Population Size must be an integer', validData)
  }else{
    setSuccessFor(populationSize, validData);
  }

  if(initialNumberOfInfectedValue===''){
    setErrorFor(initialNumberOfInfected, 'Initial Number Of Infected People cannot be blank');
  }else if(initialNumberOfInfectedValue<1){
    setErrorFor(initialNumberOfInfected, 'Initial Number Of Infected People must be at least 1')
  }else if(!isInt(initialNumberOfInfectedValue)){
    setErrorFor(initialNumberOfInfected, 'Initial Number Of Infected People must be an integer')
  }else{
    setSuccessFor(initialNumberOfInfected);
  }

  if(numberOfPeopleWhoWillBeInfectedByOnePersonValue===''){
    setErrorFor(numberOfPeopleWhoWillBeInfectedByOnePerson, 'Number Of People Who Will Be Infected By One Person cannot be blank');
  }else if(numberOfPeopleWhoWillBeInfectedByOnePersonValue<0.1){
    setErrorFor(numberOfPeopleWhoWillBeInfectedByOnePerson, 'Number Of People Who Will Be Infected By One Person must be at least 0,1')
  }else{
    setSuccessFor(numberOfPeopleWhoWillBeInfectedByOnePerson);
  }

  if(mortalityRateValue===''){
    setErrorFor(mortalityRate, 'Mortality Rate cannot be blank');
  }else if(mortalityRateValue<0.01){
    setErrorFor(mortalityRate, 'Mortality Rate must be at least 0,01')
  }else{
    setSuccessFor(mortalityRate);
  }

  if(daysFromInfectionToRecoveryeValue===''){
    setErrorFor(daysFromInfectionToRecovery, 'Days From Infection To Recovery cannot be blank');
  }else if(daysFromInfectionToRecoveryeValue<1){
    setErrorFor(daysFromInfectionToRecovery, 'Days From Infection To Recovery must be at least 1')
  }else if(!isInt(daysFromInfectionToRecoveryeValue)){
    setErrorFor(daysFromInfectionToRecovery, 'Days From Infection To Recovery must be an integer')
  }else{
    setSuccessFor(daysFromInfectionToRecovery);
  }

  if(daysFromInfectionToDeathValue===''){
    setErrorFor(daysFromInfectionToDeath, 'Days From Infection To Death cannot be blank');
  }else if(daysFromInfectionToDeathValue<1){
    setErrorFor(daysFromInfectionToDeath, 'Days From Infection To Death must be at least 1')
  }else if(!isInt(daysFromInfectionToDeathValue)){
    setErrorFor(daysFromInfectionToDeath, 'Days From Infection To Death must be an integer')
  }else{
    setSuccessFor(daysFromInfectionToDeath);
  }

  if(numberOfSimulationDaysValue===''){
    setErrorFor(numberOfSimulationDays, 'Number Of Simulation Days cannot be blank');
  }else if(numberOfSimulationDaysValue<1){
    setErrorFor(numberOfSimulationDays, 'Number Of Simulation Days must be at least 1')
  }else if(numberOfSimulationDaysValue>100){
    setErrorFor(numberOfSimulationDays, 'You can run a simulation for up to 100 days')
  }else if(!isInt(numberOfSimulationDaysValue)){
    setErrorFor(numberOfSimulationDays, 'Number Of Simulation Days must be an integer')
  }else{
    setSuccessFor(numberOfSimulationDays);
  }
}



function isInt(n){
  return n % 1 === 0;
}

function setErrorFor(input, message, validData){
  validData=false;
  let div = input.parentElement;
  let small = div.querySelector('small');

  small.innerText = message;
  small.classList.add('error');
  input.classList.add('input-error');
}

function setSuccessFor(input, validData){
  validData=true;
  let div = input.parentElement;
  let small = div.querySelector('small');

  small.classList.remove('error');
  input.classList.remove('input-error');
}


