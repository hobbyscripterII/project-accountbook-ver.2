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

function nmChk() {
    const nm = $('input[name=nm]').val();

    if (!nm) {
        alert("닉네임을 입력해주세요.");
    } else {
        $.ajax({
            type: 'get',
            url: '/nm-chk',
            data: {nm: nm},
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