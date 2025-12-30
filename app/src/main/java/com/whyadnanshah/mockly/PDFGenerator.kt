package com.whyadnanshah.mockly

import android.content.Context
import android.print.PrintManager
import android.webkit.WebView
import android.webkit.WebViewClient

class PDFGenerator(private val context: Context) {

    fun generateQuestionPaperPDF(
        course: String,
        subject: String,
        questions: String,
        difficulty: String,
        testResponse: String,
        fileName: String
    ) {
        // Create HTML content with your exact layout
        val htmlContent = createHTMLContent(course, subject, questions, difficulty, testResponse)

        // Generate PDF using WebView
        generatePDFFromHTML(htmlContent, fileName)
    }

    private fun createHTMLContent(
        course: String,
        subject: String,
        questions: String,
        difficulty: String,
        testResponse: String
    ): String {
        return """
        <!DOCTYPE html>
        <html>
        <head>
            <style>
                body { 
                    font-family: Arial, sans-serif; 
                    margin: 40px;
                    line-height: 1.6;
                }
                .header { 
                    text-align: center; 
                    margin-bottom: 30px;
                }
                .test-title {
                    font-size: 30px; 
                    font-weight: bold; 
                    margin-bottom: 24px;
                }
                .paper-info {
                    font-size: 20px;
                    margin-bottom: 8px;
                }
                .questions-section {
                    margin-top: 30px;
                }
                .question {
                    margin-bottom: 25px;
                    font-size: 14px;
                    padding-left: 10px;
                }
                .q-number {
                    font-weight: bold;
                    color: #2c3e50;
                }
            </style>
        </head>
        <body>
            <div class="header">
                <div class="test-title">TEST $course</div>
                
                <div class="paper-info">Paper Name: $subject</div>
                <div class="paper-info">No. of questions: $questions</div>
                <div class="paper-info">Difficulty: $difficulty</div>
            </div>
            
            <div class="questions-section">
                ${parseQuestionsFromResponse(testResponse)}
            </div>
        </body>
        </html>
        """
    }

    private fun parseQuestionsFromResponse(testResponse: String): String {
        return try {
            // If response is JSON array: ["Q1: question", "Q2: question"]
            if (testResponse.trim().startsWith("[")) {
                testResponse.removePrefix("[").removeSuffix("]")
                    .split("\",\"")
                    .mapIndexed { index, question ->
                        val cleanQuestion = question.removePrefix("\"").removeSuffix("\"")
                        "<div class='question'><span class='q-number'>Q${index + 1}:</span> $cleanQuestion</div>"
                    }.joinToString("")
            } else {
                // If response is plain text with Q1:, Q2: format
                testResponse.split("Q2:", "Q3:", "Q4:", "Q5:", "Q6:", "Q7:", "Q8:", "Q9:", "Q10:")
                    .mapIndexed { index, question ->
                        if (question.isNotBlank()) {
                            "<div class='question'><span class='q-number'>Q${index + 1}:</span> ${question.trim()}</div>"
                        } else ""
                    }.joinToString("")
            }
        } catch (e: Exception) {
            // Fallback: treat entire response as one question
            "<div class='question'>$testResponse</div>"
        }
    }

    private fun generatePDFFromHTML(htmlContent: String, fileName: String) {
        val webView = WebView(context)
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                createPDF(webView, fileName)
            }
        }
        webView.loadDataWithBaseURL(null, htmlContent, "text/HTML", "UTF-8", null)
    }

    private fun createPDF(webView: WebView, fileName: String) {
        val printManager = context.getSystemService(Context.PRINT_SERVICE) as PrintManager
        val printAdapter = webView.createPrintDocumentAdapter(fileName)
        val jobName = "${context.getString(R.string.app_name)} - $fileName"

        printManager.print(jobName, printAdapter, null)
    }
}