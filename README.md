# Box-PDF-Lambda
AWS Lambda Handler to process a PDF on Box and return metadata synchronously.

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
