package edu.farmingdale.bcs421_termproject

import android.util.Log
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Represents Spoonacular recipes retrieved from an external API.
 */
class Spoonacular(
    val title: String,
    val description: String,
    var sourceUrl: String,
    var image: String,
    var readyInMinutes: Int,
    var servings: Int
) {
    companion object {
        private const val apiKey = "apiKey=7089afb084ac4235a7014fa58ba91a18"
        private val recipeList = mutableListOf<Spoonacular>()

        suspend fun searchRecipes(query: String): List<Spoonacular> = withContext(Dispatchers.IO)  {
            val apiUrl = "https://api.spoonacular.com/recipes/complexSearch?query=$query&$apiKey"

            try {
                // Make the API call to get recipes based on the search query
                val jsonResponse = Spoonacular.getApiResponse(apiUrl)

                // Parse the JSON response and return a list of recipes
                return@withContext parseRecipes(jsonResponse)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return@withContext emptyList() // Return an empty list in case of an error
        }

        private fun parseRecipes(jsonResponse: JSONObject?): List<Spoonacular> {
            val recipes = mutableListOf<Spoonacular>()

            // Check if the JSON response is not null
            if (jsonResponse != null) {
                try {
                    // Get the "results" array from the JSON response
                    val results = jsonResponse.getJSONArray("results")

                    // Iterate through each recipe in the results array
                    for (i in 0 until results.length()) {
                        val recipeObject = results.getJSONObject(i)

                        // Extract relevant information from the recipe object
                        val title = recipeObject.optString("title", "Unknown Title")
                        val description = recipeObject.optString("summary", "Description Unavailable")
                        val sourceUrl = recipeObject.optString("sourceUrl", "Source Unavailable")
                        val image = recipeObject.optString("image", "Unknown Image")
                        val readyInMinutes = recipeObject.optInt("readyInMinutes", 0)
                        val servings = recipeObject.optInt("servings", 1)

                        // Create a Spoonacular object and add it to the list
                        recipes.add(Spoonacular(title, description, sourceUrl, image, readyInMinutes, servings))
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            Log.d("API_RESPONSE", "API response: $recipes")
            return recipes
        }
        suspend fun getApiResponse(apiUrl: String): JSONObject? = withContext(Dispatchers.IO) {
            try {
                // Create a URL object and open a connection
                val url = URL(apiUrl)
                val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"

                // Get the HTTP response code
                val responseCode = connection.responseCode

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Read and parse the JSON response
                    val inStream = BufferedReader(InputStreamReader(connection.inputStream))
                    var inputLine: String?
                    val response = StringBuilder()

                    while (inStream.readLine().also { inputLine = it } != null) {
                        response.append(inputLine)
                    }
                    inStream.close()

                    // Parse the JSON response
                    return@withContext JSONObject(response.toString())
                } else {
                    println("Request failed with response code: $responseCode")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return@withContext null // Return null in case of an error
        }

        suspend fun getRandRecipe(): JSONObject? = withContext(Dispatchers.IO) {
            // Define the API URL for Spoonacular recipes
            val apiUrl = "https://api.spoonacular.com/recipes/random?number=1&tags=vegetarian,dessert&$apiKey"

            try {
                // Create a URL object and open a connection
                val url = URL(apiUrl)
                val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"

                // Get the HTTP response code
                val responseCode = connection.responseCode

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Read and parse the JSON response
                    val inStream = BufferedReader(InputStreamReader(connection.inputStream))
                    var inputLine: String?
                    val response = StringBuilder()

                    while (inStream.readLine().also { inputLine = it } != null) {
                        response.append(inputLine)
                    }
                    inStream.close()

                    // Parse the JSON response
                    val jsonResponse = JSONObject(response.toString())

                    val results: JSONArray = jsonResponse.getJSONArray("recipes")

                    if (results.length() > 0) {
                        return@withContext results.getJSONObject(0)
                    } else {
                        // Handle the case when there are no recipes
                        println("No recipes found.")
                    }
                } else {
                    println("Request failed with response code: $responseCode")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return@withContext null // Return null in case of an error or no recipes
        }


        // Get multiple random recipes
        suspend fun getRandRecipes(numRecipes: Int): List<Spoonacular> {
            val recipes = mutableListOf<Spoonacular>()

            repeat(numRecipes) {
                getRandRecipe()?.let { jsonObject ->
                    // Print the JSON response
                    println(jsonObject.toString())

                    try {
                        val title = jsonObject.optString("title", "Temp Title")
                        val description = jsonObject.optString("summary", "Description Unavailable: Please visit source link")
                        val sourceUrl = jsonObject.getString("sourceUrl")
                        val image = jsonObject.optString("image", "null")
                        val readyInMinutes = jsonObject.getInt("readyInMinutes")
                        val servings = jsonObject.getInt("servings")

                        recipes.add(Spoonacular(title, description, sourceUrl, image, readyInMinutes, servings))
                    } catch (e: JSONException) {
                        println("Error parsing JSON: ${e.message}")
                    }
                }
            }

            return recipes
        }

        fun fetchRecipeInformation(recipeId: Int): JSONObject? {
            val apiUrl = "https://api.spoonacular.com/recipes/$recipeId/information?$apiKey"

            try {
                val url = URL(apiUrl)
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"

                val responseCode = connection.responseCode

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val inStream = BufferedReader(InputStreamReader(connection.inputStream))
                    val response = StringBuilder()
                    var inputLine: String?

                    while (inStream.readLine().also { inputLine = it } != null) {
                        response.append(inputLine)
                    }
                    inStream.close()

                    return JSONObject(response.toString())
                } else {
                    println("Request failed with response code: $responseCode")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }

        fun fetchNutritionLabel(recipeId: Int): String? {
            val apiUrl = "https://api.spoonacular.com/recipes/$recipeId/nutritionLabel?$apiKey"

            try {
                val url = URL(apiUrl)
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"

                // Set the Accept header to request HTML content
                connection.setRequestProperty("Accept", "text/html")

                val responseCode = connection.responseCode

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val inStream = BufferedReader(InputStreamReader(connection.inputStream))
                    val response = StringBuilder()
                    var inputLine: String?

                    while (inStream.readLine().also { inputLine = it } != null) {
                        response.append(inputLine)
                    }
                    inStream.close()

                    return response.toString()
                } else {
                    println("Request failed with response code: $responseCode")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }
    }
}