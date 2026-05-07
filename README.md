[![Android CI](https://github.com/verlane/moakey-vim/actions/workflows/test.yml/badge.svg)](https://github.com/verlane/moakey-vim/actions/workflows/test.yml)

# MoaKey-Vim

삼성 모아키 한국어 키보드의 오픈소스 재구현체 + **Vim 키맵 & 하드웨어 키보드 지원**

> [OpenMoa](https://github.com/AiOO/OpenMoa) (by [Ahn Kiwook](https://github.com/AiOO)) 를 포크하여 기능을 확장한 프로젝트입니다.

---

## 다운로드

[GitHub Releases](https://github.com/verlane/moakey-vim/releases) 에서 APK를 다운로드할 수 있습니다.

> APK 설치 전 기기 설정에서 **알 수 없는 출처 허용**이 필요합니다.

---

## 모아키란?

삼성전자가 개발한 제스처 기반 한글 입력 방식입니다.
자음 키를 누른 채 모음 방향으로 드래그하면 한 글자가 완성됩니다.
예) `ㄱ` + 오른쪽 드래그 → `가`

자세한 동작은 [공식 유튜브 영상](https://www.youtube.com/watch?v=Mcz0sSz1Ky4)을 참조하세요.

---

## 주요 기능

### 원본 기능 (Original)
- 모아키 제스처 입력 (양손/한손/모아키/모아키+)
- 쿼티(두벌식) 키보드
- 숫자패드, 방향키, 기호 패드, 전화번호패드
- 이모지 입력기

### 추가된 기능 (Added in MoaKey-Vim)

| 기능 | Feature | 설명 |
|------|---------|------|
| **Vim 키맵** | Vim Keymap | 외부 키보드에서 Tab 길게 누르기로 Vim 모드 진입. hjkl 방향키, w/b/e 단어 이동 등 |
| **하드웨어 키보드** | Hardware Keyboard | Caps Lock → Ctrl 리매핑, 단축키 커스터마이징 |
| **클립보드 관리** | Clipboard Manager | 복사 히스토리, URL 미리보기, 핀 고정, 편집 |
| **단축어** | Hotstring | 트리거 입력 시 자동으로 텍스트 치환 (예: `ㅇㄴ` → `안녕하세요`) |
| **단축키** | Shortcut Keys | 자음·모음 키 길게 눌러 커스텀 문자·문구 입력 |
| **단어 학습 & 추천** | Word Suggestion | 입력 빈도 기반 한국어/영어 자동완성 |
| **제스처 각도 조절** | Gesture Angle | 모음 판정 각도를 직접 조정 |
| **플로팅 인디케이터** | Floating Indicator | 화면 위에 떠 있는 키보드 상태 표시 |
| **한손 모드** | One-Handed Mode | 왼손/오른손/가운데 정렬 |
| **설정 백업/복원** | Settings Backup | JSON 형식으로 설정 내보내기·가져오기 |

---

## 설치 방법

### APK 직접 설치 (일반 사용자)

1. [Releases](https://github.com/verlane/moakey-vim/releases) 에서 최신 APK 다운로드
2. 기기 설정 → 앱 → 알 수 없는 출처 허용
3. APK 파일 실행하여 설치
4. 기기 설정 → 일반 관리 → 키보드 목록 및 기본값 → **모아키 Vim** 활성화
5. 키보드 전환 후 사용

### 직접 빌드 (개발자)

```bash
git clone https://github.com/verlane/moakey-vim.git
cd moakey-vim
./gradlew assembleDebug
```

[Android Studio](https://developer.android.com/studio) 설치 후 프로젝트를 열어 빌드할 수도 있습니다.

---

## 특허에 대하여

삼성전자의 모아키 입력 방식은 [터치 디바이스에서 문자 입력 방법 및 장치](https://doi.org/10.8080/1020110078022)라는
특허로 등록되어 보호되고 있습니다. 이 저장소를 공개하는 행위와 저장소에
[MIT 라이선스](LICENSE.md)를 적용하는 행위는 명시적으로든 암시적으로든 특허의
실시권을 가지지 않은 자의 특허 실시가 가능함을 의미하지 않습니다. 코드를
빌드하여 사용하실 분은 **개인의 책임 하에 [특허법](https://www.law.go.kr/%EB%B2%95%EB%A0%B9/%ED%8A%B9%ED%97%88%EB%B2%95)을 반드시 준수**해주시기 바랍니다.

---

## 기여

이슈와 PR은 언제나 환영합니다.
버그 리포트, 기능 제안, 코드 기여 모두 가능합니다.

---

## 오픈소스 라이선스

이 프로젝트는 다음 오픈소스를 포함합니다:

- [OpenMoa](https://github.com/AiOO/OpenMoa) — MIT License, Copyright (c) 2022 Ahn Kiwook
- [HangulParser](https://github.com/kimkevin/HangulParser) — MIT License

---

## 저작권

```
Copyright (c) 2022 Ahn Kiwook (original work — https://github.com/AiOO/OpenMoa)
Copyright (c) 2026 Songbum Bae (modifications)
```

MIT License — 자세한 내용은 [LICENSE.md](LICENSE.md)를 참조하세요.
