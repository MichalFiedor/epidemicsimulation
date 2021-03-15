let form = document.getElementById("form");

async function submitForm(event, form){
  event.preventDefault();
  const data = new FormData(event.target);
  const formJSON = Object.fromEntries(data.entries());
  let response =  performPostHttpRequest('http://localhost:8080/initialdata/', formJSON);
  response.then((obj)=>{
  if(localStorage.length!=0){
    localStorage.clear
  }
    localStorage.setItem("simulations", JSON.stringify(obj));
    window.location.href="/charts"
  });
}

form.addEventListener('submit', submitForm);

let button = document.getElementById("btnSubmit");

button.addEventListener('click', saveAllValuesFromInputsInLocalStorage);

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



