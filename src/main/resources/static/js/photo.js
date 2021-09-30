var count=1
function playMusic(){
    if(count <2){
    var myAuto = document.getElementById('bg-music');
    myAuto.play();
    count++;
    }
}