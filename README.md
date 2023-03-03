
# JavaWebAppHomework


This is a Spring Boot Java web application that provides endpoints for performing mathematical calculations and retrieving countries
## API Reference

#### Calculates the result of a short mathematical equation provided in the request body

```http
  POST /calculator/short
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `equation`| `string` | **Required**. Equation, ApiKey     |

#### Calculates the result of a long mathematical equation provided in the request body

```http
  POST /calculator/long
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `equation`| `string` | **Required**. Equation, ApiKey    |

#### Finds the highest and lowest numbers in an array of numbers
```http
  POST /calculator/high-low
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `numbers`| `array` | **Required**. Numbers, ApiKey |

#### Gets all countries in a sorted list and current location
```http
  GET /countries
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `none`| `none` | **Required**. ApiKey |


## Appendix

ApiKey is a header which acts as a authentication, without it or the correct key, making api calls won't be possible
## Author

- [@KarlisLauris](https://github.com/KarlisLauris)

