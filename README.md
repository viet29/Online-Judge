# Đề tài: Use case chấm mã nguồn
## Thành viên:
| Thành viên | Mã sinh viên |
| ------ | ------ |
| Phạm Quốc Việt | B20DCCN732 |
| Cao Xuân Trung | B20DCCN696 |
| Đinh Hữu Nam | B20DCCN026 |
## Phân tích:
Link: https://docs.google.com/document/d/1Rf-49Q_AbWybXDOKCePDgWNGzyfLQzpmcU31sTT9R84/edit?usp=sharing

## Thiết kế
### Bản thiết kế OPEN API bằng YAML
```
---
openapi: 3.0.1
info:
  title: OpenAPI definition
  version: v0
servers:
- url: http://localhost:8080
  description: Generated server url
paths:
  "/api/topics":
    get:
      tags:
      - topic-controller
      operationId: getAllTopics
      responses:
        '200':
          description: OK
          content:
            "*/*":
              schema:
                type: array
                items:
                  "$ref": "#/components/schemas/Topic"
    post:
      tags:
      - topic-controller
      operationId: createTopic
      requestBody:
        content:
          application/json:
            schema:
              "$ref": "#/components/schemas/Topic"
        required: true
      responses:
        '200':
          description: OK
          content:
            "*/*":
              schema:
                "$ref": "#/components/schemas/Topic"
  "/api/submit/{exerciseId}":
    post:
      tags:
      - submit-controller
      operationId: submit
      parameters:
      - name: language
        in: query
        required: true
        schema:
          type: integer
          format: int32
      - name: exerciseId
        in: path
        required: true
        schema:
          type: integer
          format: int32
      requestBody:
        content:
          multipart/form-data:
            schema:
              required:
              - file
              type: object
              properties:
                file:
                  type: string
                  format: binary
      responses:
        '200':
          description: OK
          content:
            "*/*":
              schema:
                type: object
  "/api/submissions":
    get:
      tags:
      - submission-controller
      operationId: getAllSubmissions
      responses:
        '200':
          description: OK
          content:
            "*/*":
              schema:
                type: array
                items:
                  "$ref": "#/components/schemas/Submission"
    post:
      tags:
      - submission-controller
      operationId: addSubmission
      requestBody:
        content:
          application/json:
            schema:
              "$ref": "#/components/schemas/Submission"
        required: true
      responses:
        '200':
          description: OK
          content:
            "*/*":
              schema:
                type: object
  "/api/testcases/{exerciseId}":
    get:
      tags:
      - test-case-controller
      operationId: getTestCases
      parameters:
      - name: exerciseId
        in: path
        required: true
        schema:
          type: integer
          format: int32
      responses:
        '200':
          description: OK
          content:
            "*/*":
              schema:
                type: array
                items:
                  "$ref": "#/components/schemas/TestCase"
  "/api/submissions/{id}":
    get:
      tags:
      - submission-controller
      operationId: getSubmissionById
      parameters:
      - name: id
        in: path
        required: true
        schema:
          type: integer
          format: int32
      responses:
        '200':
          description: OK
          content:
            "*/*":
              schema:
                type: object
  "/api/exercises":
    get:
      tags:
      - exercise-controller
      operationId: getAllExercises
      parameters:
      - name: topicId
        in: query
        required: false
        schema:
          type: integer
          format: int32
      responses:
        '200':
          description: OK
          content:
            "*/*":
              schema:
                type: object
  "/api/exercises/{id}":
    get:
      tags:
      - exercise-controller
      operationId: getExerciseDetailById
      parameters:
      - name: id
        in: path
        required: true
        schema:
          type: integer
          format: int32
      responses:
        '200':
          description: OK
          content:
            "*/*":
              schema:
                type: object
components:
  schemas:
    Topic:
      type: object
      properties:
        id:
          type: integer
          format: int32
        name:
          type: string
    Exercise:
      type: object
      properties:
        exerciseId:
          type: integer
          format: int32
        ma:
          type: string
        name:
          type: string
        detail:
          type: string
        timeLimit:
          type: number
          format: double
        memoryLimit:
          type: integer
          format: int32
        level:
          type: string
          enum:
          - Hard
          - Medium
          - Easy
        topic:
          "$ref": "#/components/schemas/Topic"
    Submission:
      type: object
      properties:
        id:
          type: integer
          format: int32
        output:
          type: string
        actualTime:
          type: number
          format: double
        actualMemory:
          type: integer
          format: int32
        language:
          type: string
        status:
          type: string
          enum:
          - WA
          - AC
          - TLE
          - RTE
          - MLE
          - CE
        content:
          type: string
        dateSubmit:
          type: string
          format: date-time
        exercise:
          "$ref": "#/components/schemas/Exercise"
        user:
          "$ref": "#/components/schemas/User"
    User:
      type: object
      properties:
        id:
          type: integer
          format: int32
        username:
          type: string
        password:
          type: string
        fullName:
          type: string
        email:
          type: string
    TestCase:
      type: object
      properties:
        id:
          type: integer
          format: int32
        input:
          type: string
        output:
          type: string
        exercise:
          "$ref": "#/components/schemas/Exercise"
        isHidden:
          type: boolean
```

### Công nghệ:
  - Framework Spring Boot, MySQL.

