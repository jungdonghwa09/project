/**
파일 업로드 후 처리 함수-files는 업로드 한 파일 목록
*/
function callbackFileUpload(files){
    if(!files || files.length ==0){
    return;
    }
    const file =files[0];
    let html = document.getElementById("image1_tpl").innerHTML;
    //이미지를 검색해서 html

    const imageUrl = file.thumbsUrl.length > 0 ? file.thumbsUrl.pop() : file.fileUrl;
    cons seq = file.seq;
    //삭제하기 위해서 seq가져옴
    html = html.replace(/\[seq\]/g, seq)
                .replace(/\[imageUrl\]/g, imageUrl);
    const domParser = new DOMParser();
    const dom = domParser.parseFromString(html, "text/html");

    const imageTplEl = dom.querySelector(".image1_tpl_box");
    const profileImage = document.getElementById("profile_image");
    profileImage.innerHTML="";

    profileImage.appendChild(imageTplEl);

}
//파일삭제 후속 처리
function callbackFileDelete(seq){
const fileEl = document.getElementById(`file_${seq}`)

}