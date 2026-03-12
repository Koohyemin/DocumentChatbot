# rag-doc-chatbot

Spring Boot + Ollama + PostgreSQL(pgvector) 기반의 로컬 RAG 문서 챗봇 프로젝트입니다.

사용자가 문서를 업로드하면 텍스트를 chunk 단위로 분리하고 embedding을 저장한 뒤,
질문 입력 시 유사한 문맥을 검색하여 LLM이 답변과 출처를 함께 반환합니다.

---

## 1. 프로젝트 소개

이 프로젝트는 문서 기반 질의응답(RAG, Retrieval-Augmented Generation) 흐름을
Java/Spring 환경에서 직접 구현하는 것을 목표로 합니다.

단순히 LLM을 호출하는 수준이 아니라,
문서 업로드 → 텍스트 추출 → chunk 분리 → embedding 저장 → 벡터 검색 → 답변 생성까지
전체 파이프라인을 백엔드 중심으로 설계하고 구현합니다.

### 주요 목표
- 로컬 환경에서 비용 없이 실행 가능한 RAG 서비스 구현
- Spring Boot 기반 AI 백엔드 아키텍처 경험
- 벡터 검색과 LLM 연동 흐름 이해
- 출처 기반 답변 제공

---

## 2. 기술 스택

### Backend
- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- Validation
- Lombok

### AI / RAG
- Ollama
- Spring AI
- pgvector

### Database
- PostgreSQL

### ETC
- Gradle
- Thymeleaf (optional)
- Docker / Docker Compose (optional)

---

## 3. 핵심 기능

- 문서 업로드 (txt, md, pdf)
- 문서 텍스트 추출
- 문서 chunk 분리
- chunk embedding 생성 및 저장
- 질문 입력 시 유사 chunk 검색
- LLM 답변 생성
- 답변 근거 chunk 반환
- 질문 이력 저장

---

## 4. 프로젝트 구조

```text
com.example.ragchatbot
 ┣ common
 ┃ ┣ config
 ┃ ┣ exception
 ┃ ┣ response
 ┃ ┗ util
 ┣ document
 ┃ ┣ controller
 ┃ ┣ service
 ┃ ┣ domain
 ┃ ┣ dto
 ┃ ┣ repository
 ┃ ┗ parser
 ┣ chat
 ┃ ┣ controller
 ┃ ┣ service
 ┃ ┣ dto
 ┃ ┣ domain
 ┃ ┗ repository
 ┣ embedding
 ┃ ┣ service
 ┣ ai
 ┃ ┣ service
 ┃ ┗ prompt
 ┗ RagChatbotApplication