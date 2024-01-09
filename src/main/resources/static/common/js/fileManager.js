var commonLib = commonLib || {};
/**
* 1. 파일 업로드
*
*/
commonLib.fileManager = {
    /**
    * 파일 업로드 처리
    *
    */
    upload(files, location, imageOnly, singleFile) {
    //files 업로드 파일 정보목록, imageOnly 이미지만 업로드
    //location은 파일 그룹(gid)안에서 위치구분-메인이미지, 목록이미지, 상세이미지
    try{
        if(!files || files.length==0){
            throw new Error("업로드할 파일을 선택하세요");
        }

        //gid를 가져오기
        const gidEl = document.querySelector("[name='gid']");
        if(!gidEl || !gidEl.value.trim()){
            throw new Error("gid가 누락되었습니다");
        }
        const gid = gidEl.value.trim();
        const formData = new FormData();//기본 content-type=multipart
        formData.append("gid", gid);

        if(location){
        formData.append("location", location);
        }

        if(singleFile){
        formData.append("singleFile", singleFile);
        }
        //이미지만업로드 가능할때 처리
                if(imageOnly){
                    for(const fileof files){
                        if(file.type.indexOf("image/") == -1){
                        //이미지 형식이 아닌 파일이 포함된 경우
                        throw new Error("이미지 형식의 파일만 업로드 가능합니다.");
                    }
               }
               formData.append("imageOnly", imageOnly);
        }

        for(const file of files){
        formData.append("file",file);
        }//새로고침 안해도 데이터를치환하는 a-jax

        const{ ajaxLoad } = commonLib;
        ajaxLoad("POST", "/api/file", formData,"json")
        .then((res)=> {
        //요청 성공 시
            if(res && res.success){//파일 업로드 성공

                if(typeof parent.callbackFileUpload =='function'){
                parent.callbackFileUpload(res.data);
                }

            }else{//파일 업로드 실패
                if(res) alert(res.message);
            }
        })
        .catch(err=> console.error(err));

    }catch(err){
        alert(err.message);
        console.error(err);
    }
    }
};


// 이벤트 처리
window.addEventListener("DOMContentLoaded", function() {
    const uploadFiles = document.getElementsByClassName("upload_files");


    for (const el of uploadFiles) {//파일 업로드 버튼 눌렀을 때 파일 탐색기 열기
        el.addEventListener("click", function() {

        const fileEl = document.createElement("input");
            fileEl.type="file";
            fileEl.multiple=true;


            const imageOnly = this.dataset.imageOnly == 'true';
            fileEl.imageOnly = imageOnly;

            fileEl.location = this.dataset.location;
            const singleFile = this.dataset.singleFile == 'true';
            fileEl.singleFile = singleFile;

            if(singleFile) fileEl.multiple=false;

            //파일선택시 이벤트 처리

                            fileEl.addEventListener("change", function(e){
                            const imageOnly = fileEl.imageOnly || false;
                            const location = fileEl.location;
                            const singleFile = fileEl.singleFile;
                            commonLib.fileManager.upload(e.target.files, location, imageOnly, singleFile);
                            });
            fileEl.click();
        });
    }


});