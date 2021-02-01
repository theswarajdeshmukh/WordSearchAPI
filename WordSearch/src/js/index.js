import { Grid } from "./grid";


const submitWordBtn = document.querySelector(".submit-word");
//const grid = new Grid();


submitWordBtn.addEventListener("click", async () =>{
  //  const grid = new Grid();
  //  const commaSeparatedWords = document.querySelector("#add-word").value;
  //  const gridSize = document.querySelector("#grid-size").value;
  //  let result = await fetchGridInfo(gridSize, commaSeparatedWords);
  //  grid.words = commaSeparatedWords.split(","); //checking if user has selected correct
  //  grid.renderGrid(gridSize, result);
  const grid = new Grid();
  const commaSeperatedWords = document.querySelector("#add-word").value;
  const gridSize = document.querySelector("#grid-size").value;
  let result = await fetchGridInfo(gridSize, commaSeperatedWords);
  grid.words = commaSeperatedWords.split(",");
  grid.renderGrid(gridSize, result);
  let wordListNode = document.createTextNode(grid.words);
  let wordListSection = document.querySelector(".word-list");
  if (wordListSection.lastChild) {
      wordListSection.removeChild(wordListSection.lastChild);
  }
  wordListSection.appendChild(wordListNode);
  
  
});

async function fetchGridInfo(gridSize, commaSeparatedWords){

  //  const commaSeparatedWords = wordList.join(",");
  let response = await fetch(`http://localhost:8080/wordgrid?gridSize=${gridSize}&wordList=${commaSeparatedWords}`);
  let result = await response.text();
  return result.split(" ");
}