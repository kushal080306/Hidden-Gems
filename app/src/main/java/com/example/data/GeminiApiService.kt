package com.example.data

import com.example.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

// --- Data Classes for Request/Response ---

data class GenerateContentRequest(
    val contents: List<Content>,
    val systemInstruction: Content? = null
)

data class Content(
    val parts: List<Part>
)

data class Part(
    val text: String
)

data class GenerateContentResponse(
    val candidates: List<Candidate>?
)

data class Candidate(
    val content: Content?
)

// --- Retrofit Setup ---

interface GeminiApiService {
    @POST("v1beta/models/gemini-3.5-flash:generateContent")
    suspend fun generateContent(
        @Query("key") apiKey: String,
        @Body request: GenerateContentRequest
    ): GenerateContentResponse
}

object RetrofitClient {
    private const val BASE_URL = "https://generativelanguage.googleapis.com/"

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    val service: GeminiApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(GeminiApiService::class.java)
    }
}

// --- Helper Object ---

object GeminiAssistant {
    private val systemInstructionText = """
        You are an AI Tourist Assistant for 'Shivamogga Hidden Gems'.
        You help users discover lesser-known and hidden tourist destinations around Shivamogga, Karnataka.
        You can answer in both English and Kannada.
        Keep your answers concise, helpful, and friendly.
    """.trimIndent()

    suspend fun askQuestion(prompt: String, conversationHistory: List<com.example.model.ChatMessage> = emptyList()): String {
        return withContext(Dispatchers.IO) {
            val apiKey = BuildConfig.GEMINI_API_KEY
            if (apiKey.isEmpty() || apiKey == "MY_GEMINI_API_KEY") {
                return@withContext "Error: Gemini API key is missing. Please configure it in AI Studio Secrets."
            }

            val contents = mutableListOf<Content>()
            // Add history
            for (msg in conversationHistory) {
                // In a real app we'd map roles properly, but for simplicity we'll just prefix text if user vs model
                val rolePrefix = if (msg.isUser) "User: " else "Assistant: "
                contents.add(Content(listOf(Part(text = rolePrefix + msg.text))))
            }
            contents.add(Content(listOf(Part(text = prompt))))

            val request = GenerateContentRequest(
                contents = contents,
                systemInstruction = Content(listOf(Part(text = systemInstructionText)))
            )

            try {
                val response = RetrofitClient.service.generateContent(apiKey, request)
                response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text ?: "I'm sorry, I couldn't process that request."
            } catch (e: Exception) {
                "Network Error: ${e.message}"
            }
        }
    }
}
