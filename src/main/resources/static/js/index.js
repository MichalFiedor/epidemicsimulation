let form = document.getElementById("form");

async function submitForm(event, form){
  event.preventDefault();

  const data = new FormData(event.target);
  const formJSON = Object.fromEntries(data.entries());
  let response =  performPostHttpRequest('http://localhost:8080/initialdata/', formJSON);
  console.log(response);
  response.then((obj)=>{
    sessionStorage.clear;
    sessionStorage.setItem("simulations", JSON.stringify(obj));
  });
//  window.location.href="/charts";
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

form.addEventListener('submit', submitForm);

