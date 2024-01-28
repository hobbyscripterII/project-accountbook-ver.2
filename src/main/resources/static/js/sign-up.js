
function signUp() {
    const email = $('input[name=email]').val();
    const upw = $('input[name=upw]').val();
    const nm = $('input[name=nm]').val();
    const gender = $('input[name=gender]');
    const birth = $('input[name=birth]').val();
    const chkTeamsOfUse = $('#chk-teams-of-use');
    const formSignUp = $('#form-sign-up');
    const emailChk = $('input[name=email]').data('email-chk');
    const nmChk = $('input[name=nm]').data('nm-chk');

    if (chkTeamsOfUse.is(':checked') == false) {
        alert("이용약관에 동의해주세요.");
    } else if (!email) {
        alert("이메일을 입력해주세요.");
    } else if (!upw) {
        alert("비밀번호를 입력해주세요.");
    } else if (!nm) {
        alert("닉네임을 입력해주세요.");
    } else if (gender.is(':checked') == false) {
        alert("성별을 선택해주세요.");
    } else if (!birth) {
        alert("생년월일을 선택해주세요.");
    } else if (nmChk == 0) {
        alert("닉네임 중복 확인이 완료되지 않았습니다.");
    } else {
        formSignUp.submit();
    }

    // if(chkTeamsOfUse.is(':checked') == false) {
    //     alert("이용약관에 동의해주세요.");
    // } else if(!email) {
    //     alert("이메일을 입력해주세요.");
    // } else if(!upw) {
    //     alert("비밀번호를 입력해주세요.");
    // } else if(!nm) {
    //     alert("닉네임을 입력해주세요.");
    // } else if(gender.is(':checked') == false) {
    //     alert("성별을 선택해주세요.");
    // } else if(!birth) {
    //     alert("생년월일을 선택해주세요.");
    // } else if(emailChk == 0) {
    //     alert("이메일 인증이 완료되지 않았습니다.");
    // } else if(nmChk == 0) {
    //     alert("닉네임 중복 확인이 완료되지 않았습니다.");
    // } else {
    //     formSignUp.submit();
    // }
}

function emailSend() {
    // 이메일 정규식
    let regex = new RegExp("([!#-'*+/-9=?A-Z^-~-]+(\.[!#-'*+/-9=?A-Z^-~-]+)*|\"\(\[\]!#-[^-~ \t]|(\\[\t -~]))+\")@([!#-'*+/-9=?A-Z^-~-]+(\.[!#-'*+/-9=?A-Z^-~-]+)*|\[[\t -Z^-~]*])");
    let email = $('input[name=email]').val();

    if(email === null || !regex.test(email)) {
        alert("이메일 형식에 맞춰 입력해주세요.");
    } else {
        $.ajax({
            type: 'get',
            url: '/email/send',
            data: {email : email},
            success: function (data) {
                if (data === 2) {
                    alert("이미 가입된 이메일입니다. 다른 이메일을 입력해주세요.");
                } else if(data === 1) {
                    alert("잠시 후 다시 시도해주세요.");
                } else {
                    alert("이메일 확인 후 인증 코드를 입력해주세요.");
                    $('#tr-email-auth').css('display', 'block'); // 이메일 인증 코드 폼 활성화
                }
            }
        })
    }
}

function emailAuthChk() {
    const email = $('input[name=email]').val();
    const emailAuthCode = $('#input-email-auth-code');
    // dto에 담으려면 프로퍼티명이랑 맞추기
    const dto = {email : email, emailAuthCode: emailAuthCode.val()};

    $.ajax({
        type: 'post',
        url: '/email/auth-chk',
        contentType: "application/json", // http 요청 헤더 content-type을 application/json으로 맞춤
        data: JSON.stringify(dto), // json 형태로 보내기 위한 작업
        success: function (data) {
            if (data === 1) {
                alert("이메일 인증이 완료되었습니다.");
                emailAuthCode.data('email-auth-chk', 1); // data-set 속성 변경
                console.log(emailAuthCode.data('email-auth-chk')); // data-set 속성 확인
            } else {
                alert("이메일 인증 코드가 일치하지 않습니다.");
                emailAuthCode.data('email-auth-chk', 0);
                console.log(emailAuthCode.data('email-auth-chk'));
            }
        }
    })
}

function nmChk() {
    const nm = $('input[name=nm]').val();

    if (!nm) {
        alert("닉네임을 입력해주세요.");
    } else {
        $.ajax({
            type: 'get',
            url: '/nm-chk',
            data: {nm : nm},
            success: function (data) {
                if (data === 1) {
                    alert("이미 등록된 닉네임입니다. 다른 닉네임을 입력해주세요.");
                    $('input[name=nm]').data('nm-chk', 0);
                    console.log($('input[name=nm]').data('nm-chk'));
                } else {
                    alert("사용 가능한 닉네임입니다.");
                    $('input[name=nm]').data('nm-chk', 1);
                    console.log($('input[name=nm]').data('nm-chk'));
                }
            }
        })
    }
}