# Box-PDF-Lambda
AWS Lambda Handler to process a PDF on Box and return metadata synchronously.

### API URL
    https://zc5cpkqhrg.execute-api.us-west-2.amazonaws.com/prod/BoxPDFService

#### API Key Request Header
    x-api-key: <api gateway key>

### Request
```
{
  "boxAuthToken": "xxx...",
  "boxFileId": "000..."
}
```

### Response
```
{
  "status": "OK",
  "metadata": {
    "CreationDate": "D:20160726074934-05'00'",
    "Producer": "Adobe PDF Library 15.0",
    "Creator": "Adobe InDesign CC 2015 (Macintosh)",
    "ModDate": "D:20160726095425-05'00'",
    "Trapped": null
  },
  "png": "iVBORw0KGgoAAAANSUhEUgAAAxgAAAJkCAIAAAAdiBA9AACAAElEQVR42uy9eXQU1533PX/knNd/5DxPzknOJJksk5lhEmccv/ZM...",
  "sha1": "41167334819d7a3298006a32ae43e82c9bc234eb",
  "frontText": "BEGINNER/NOVICE\nADVANCED EXPERTINTERMEDIATE\nCertifications validate expertise in your chosen career.\nIT Certification..."
}
```
### Curl Example
    curl -H "Content-Type: application/json" -H "x-api-key: csNL1Y1PeL3RpqvERPpZr7rfqBPLYye62MoimifT" -d '{"boxAuthToken":"abc","boxFileId":"123"}' https://zc5cpkqhrg.execute-api.us-west-2.amazonaws.com/prod/BoxPDFService
