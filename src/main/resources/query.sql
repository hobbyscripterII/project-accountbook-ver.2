CREATE TABLE user_tbl (
                          uid INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
                          email VARCHAR(50) UNIQUE,
                          google_id VARCHAR(100) UNIQUE,
                          kakao_id VARCHAR(100) UNIQUE,
                          upw VARCHAR(100) NOT NULL,
                          role CHAR(1) NOT NULL DEFAULT 0,
                          nm VARCHAR(10) UNIQUE NOT NULL,
                          gender CHAR(1) NOT NULL CHECK (gender IN ('M', 'W')),
                          age CHAR(3) NOT NULL CHECK (age >= 14),
                          visit_cnt INT UNSIGNED NOT NULL DEFAULT 0,
                          del_fl CHAR(1) NOT NULL DEFAULT 'N',
                          created_at DATETIME NOT NULL DEFAULT NOW(),
                          updated_at DATETIME ON UPDATE NOW(),
                          deleted_at DATETIME,
                          first_created_at DATETIME,
                          first_created_user INT UNSIGNED,
                          last_created_at DATETIME,
                          last_created_user INT UNSIGNED
);

# 방법1 - 회원가입 직후 시스템 로그 업데이트
INSERT INTO user_tbl(email, upw, nm, gender, age)
VALUES ('jy@gmail.com', 'test!', '이주영', 'W', 31);

UPDATE user_tbl SET
                    first_created_at = NOW(),
                    first_created_user = 1
WHERE uid = 1;

# 방법2 - 회원가입 시 INSERT문 내에 서브쿼리를 통해 PK 값을 INSERT
INSERT INTO user_tbl(email, upw, nm, gender, age, first_created_at, first_created_user)
VALUES ('yj@gmail.com', 'test!', '이영주', 'W', 31, NOW(), (SELECT IFNULL(MAX(uid) + 1, 1) FROM user_tbl u));

CREATE TABLE data_tbl (
                          idata INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
                          iuser INT UNSIGNED NOT NULL,
                          data_nm VARCHAR(20) NOT NULL DEFAULT '기본 데이터',
                          mid_target_save_amount INT UNSIGNED NOT NULL, # 중장기 목표 저축 금액
                          month_target_save_amount INT UNSIGNED NOT NULL, # 한달 목표 저축 금액
                          month_fix_budget_amount INT UNSIGNED NOT NULL, # 한달 고정지출 예산 금액
                          month_nonfix_budget_amount INT UNSIGNED NOT NULL, # 한달 생활비 예산 금액
                          first_created_at DATETIME NOT NULL DEFAULT NOW(),
                          first_created_user INT UNSIGNED NOT NULL,
                          last_created_at DATETIME ON UPDATE NOW(),
                          last_created_user INT UNSIGNED,
                          FOREIGN KEY (iuser) REFERENCES user_tbl (iuser) ON DELETE CASCADE
);

CREATE TABLE month_code_tbl (
                                imonthcode INT UNSIGNED NOT NULL,
                                iuser INT UNSIGNED NOT NULL,
                                PRIMARY KEY (imonthcode, iuser),
                                FOREIGN KEY (iuser) REFERENCES user_tbl (iuser) ON DELETE CASCADE
);

CREATE TABLE month_data_tbl (
                                imonthcode INT UNSIGNED NOT NULL,
                                iuser INT UNSIGNED NOT NULL,
                                amount INT UNSIGNED NOT NULL,
                                month_data_view_fl CHAR(1) NOT NULL DEFAULT 'Y',
                                PRIMARY KEY (imonthcode, iuser),
                                FOREIGN KEY (imonthcode) REFERENCES month_code_tbl (imonthcode) ON DELETE CASCADE,
                                FOREIGN KEY (iuser) REFERENCES user_tbl (iuser) ON DELETE CASCADE
);

CREATE TABLE asset_category_tbl (
                                    iassetcategory INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
                                    iuser INT UNSIGNED NOT NULL,
                                    asset_category_code CHAR(1) NOT NULL, # 1. 현금 2. 투자 3. 기타 4. 부채
                                    asset_category_nm VARCHAR(20) NOT NULL, # 자산현황 카테고리명
                                    first_created_at DATETIME NOT NULL DEFAULT NOW(),
                                    first_created_user INT UNSIGNED NOT NULL,
                                    last_created_at DATETIME ON UPDATE NOW(),
                                    last_created_user INT UNSIGNED,
                                    FOREIGN KEY (iuser) REFERENCES user_tbl (iuser) ON DELETE CASCADE
);

CREATE TABLE accountbook_category_tbl (
                                          iaccountbookcategory INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
                                          iuser INT UNSIGNED NOT NULL,
                                          ac_category_code CHAR(1) NOT NULL, # 1. 수입 2. 저축 3. 고정지출 4. 비고정지출
                                          ac_category_nm VARCHAR(20) NOT NULL, # 가계부 카테고리명
                                          first_created_at DATETIME NOT NULL DEFAULT NOW(),
                                          first_created_user INT UNSIGNED NOT NULL,
                                          last_created_at DATETIME ON UPDATE NOW(),
                                          last_created_user INT UNSIGNED,
                                          FOREIGN KEY (iuser) REFERENCES user_tbl (iuser) ON DELETE CASCADE
);

CREATE TABLE asset_tbl (
                           imonthcode INT UNSIGNED NOT NULL,
                           iassetcategory INT UNSIGNED NOT NULL,
                           iuser INT UNSIGNED NOT NULL,
                           amount INT UNSIGNED NOT NULL,
                           tbl_view_fl CHAR(1) NOT NULL DEFAULT 'Y',
                           pf_view_fl CHAR(1) NOT NULL DEFAULT 'Y',
                           first_created_at DATETIME NOT NULL DEFAULT NOW(),
                           first_created_user INT UNSIGNED NOT NULL,
                           last_created_at DATETIME ON UPDATE NOW(),
                           last_created_user INT UNSIGNED,
                           PRIMARY KEY (imonthcode, iassetcategory, iuser),
                           FOREIGN KEY (imonthcode) REFERENCES month_data_tbl (imonthcode) ON DELETE CASCADE,
                           FOREIGN KEY (iassetcategory) REFERENCES asset_category_tbl (iassetcategory) ON DELETE CASCADE,
                           FOREIGN KEY (iuser) REFERENCES user_tbl (iuser) ON DELETE CASCADE
);

CREATE TABLE accountbook_tbl (
                                 imonthcode INT UNSIGNED NOT NULL,
                                 iaccountbookcategory INT UNSIGNED NOT NULL,
                                 iuser INT UNSIGNED NOT NULL,
                                 amount INT UNSIGNED NOT NULL,
                                 tbl_view_fl CHAR(1) NOT NULL DEFAULT 'Y',
                                 first_created_at DATETIME NOT NULL DEFAULT NOW(),
                                 first_created_user INT UNSIGNED NOT NULL,
                                 last_created_at DATETIME ON UPDATE NOW(),
                                 last_created_user INT UNSIGNED,
                                 PRIMARY KEY (imonthcode, iaccountbookcategory, iuser),
                                 FOREIGN KEY (imonthcode) REFERENCES month_data_tbl (imonthcode) ON DELETE CASCADE,
                                 FOREIGN KEY (iaccountbookcategory) REFERENCES accountbook_category_tbl (iaccountbookcategory) ON DELETE CASCADE,
                                 FOREIGN KEY (iuser) REFERENCES user_tbl (iuser) ON DELETE CASCADE
);

CREATE TABLE asset_backup_tbl (
                                  iasset INT UNSIGNED NOT NULL,
                                  iassetcategory INT UNSIGNED NOT NULL,
                                  iuser INT UNSIGNED NOT NULL,
                                  amount INT UNSIGNED NOT NULL,
                                  first_created_at DATETIME NOT NULL DEFAULT NOW(),
                                  first_created_user INT UNSIGNED NOT NULL,
                                  PRIMARY KEY (iasset, iassetcategory, iuser),
                                  FOREIGN KEY (iasset) REFERENCES asset_tbl (iasset),
                                  FOREIGN KEY (iassetcategory) REFERENCES asset_category_tbl (iassetcategory),
                                  FOREIGN KEY (iuser) REFERENCES user_tbl (iuser)
);

CREATE TABLE asset_backup_tbl (
                                  iassetcategory INT UNSIGNED NOT NULL,
                                  imonthcode INT UNSIGNED NOT NULL,
                                  iuser INT UNSIGNED NOT NULL,
                                  amount INT UNSIGNED NOT NULL,
                                  first_created_at DATETIME NOT NULL DEFAULT NOW(),
                                  first_created_user INT UNSIGNED NOT NULL
);

CREATE TABLE accountbook_backup_tbl (
                                        iaccountbookcategory INT UNSIGNED NOT NULL,
                                        imonthcode INT UNSIGNED NOT NULL,
                                        iuser INT UNSIGNED NOT NULL,
                                        amount INT UNSIGNED NOT NULL,
                                        first_created_at DATETIME NOT NULL DEFAULT NOW(),
                                        first_created_user INT UNSIGNED NOT NULL
);