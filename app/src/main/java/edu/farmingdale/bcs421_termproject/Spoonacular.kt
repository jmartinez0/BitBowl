package edu.farmingdale.bcs421_termproject

import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

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

        fun getRandRecipe() {
            // Define the API URL for Spoonacular recipes
            val apiUrl = "https://api.spoonacular.com/recipes/random?number=1&tags=vegetarian,dessert&$apiKey"

            try {
                // Create a URL object and open a connection
                val url = URL(apiUrl)
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"

                // Get the HTTP response code
                val responseCode = connection.responseCode

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Read and parse the JSON response
                    val inStream = BufferedReader(InputStreamReader(connection.inputStream))
                    val response = StringBuilder()
                    var inputLine: String?

                    while (inStream.readLine().also { inputLine = it } != null) {
                        response.append(inputLine)
                    }
                    inStream.close()

                    // Parse the JSON response
                    val jsonResponse = JSONObject(response.toString())

                    val results = jsonResponse.getJSONArray("recipes")

                    for (i in 0 until results.length()) {
                        val recipe = results.getJSONObject(i)
                        val title = recipe.getString("title")
                        var description = "Description Unavailable: Please visit source link."

                        if (recipe.has("summary") && !recipe.isNull("summary")) {
                            description = recipe.getString("summary")
                        }

                        val sourceUrl = recipe.getString("sourceUrl")
                        var image = "null"

                        if (recipe.has("image") && !recipe.isNull("image")) {
                            image = recipe.getString("image")
                        }

                        val readyInMinutes = recipe.getInt("readyInMinutes")
                        val servings = recipe.getInt("servings")

                        // Now you have various details in separate variables
                        println("Title: $title")
                        println("Description: $description")
                        println("Source URL: $sourceUrl")
                        println("Image URL: $image")
                        println("Ready In Minutes: $readyInMinutes")
                        println("Servings: $servings")

                        // Extract and print analyzedInstructions
                        val analyzedInstructions = recipe.getJSONArray("analyzedInstructions")
                        for (j in 0 until analyzedInstructions.length()) {
                            val instruction = analyzedInstructions.getJSONObject(j)
                            val name = instruction.getString("name")
                            println("Instruction Name: $name")
                            val steps = instruction.getJSONArray("steps")
                            for (k in 0 until steps.length()) {
                                val step = steps.getJSONObject(k)
                                val stepNumber = step.getInt("number")
                                val stepDescription = step.getString("step")
                                println("Step $stepNumber: $stepDescription")
                            }
                        }

                        // Create a Spoonacular object and add it to the list
                        recipeList.add(Spoonacular(title, description, sourceUrl, image, readyInMinutes, servings))
                    }
                } else {
                    println("Request failed with response code: $responseCode")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
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