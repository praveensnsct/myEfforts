/**
 * Created by pthiru on 2/8/2016.
 */
function output(err,output){
    if(err)console.error(err);
    if(output)console.log(output);
}

function initArr(size,callback){
    try{
        var arr = new Array();
        for (var i = 0; i < size; i++) {
            arr[i] = i;
        }
        callback(null,arr);
    }
    catch(ex){
        callback(ex,arr);
    }
}

setTimeout(function(){
initArr(20,output);
},1000);

console.log("Going ahead..");