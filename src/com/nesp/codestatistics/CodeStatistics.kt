package com.nesp.codestatistics

import java.io.File

class CodeStatistics(private val dirs: Array<String>) {

    private var javaFileCount = 0
    private var javaFileLineCount = 0
    private var javaFileCodeLineCount = 0
    private var javaFileEmptyLineCount = 0
    private var javaFileNoteLineCount = 0

    private var kotlinFileCount = 0
    private var kotlinFileLineCount = 0
    private var kotlinFileCodeLineCount = 0
    private var kotlinFileEmptyLineCount = 0
    private var kotlinFileNoteLineCount = 0

    private var xmlFileCount = 0
    private var xmlFileLineCount = 0
    private var xmlFileCodeLineCount = 0
    private var xmlFileEmptyLineCount = 0
    private var xmlFileNoteLineCount = 0

    fun start() {
        for (dir in dirs) {
            val rootFile = File(dir)
            analyse(rootFile)
        }


        println("""
            Summary：

            Java File Count:$javaFileCount
            Java File Line Count:$javaFileLineCount
            Java File Empty Line Count:$javaFileEmptyLineCount
            Java File Note Line Count:$javaFileNoteLineCount
            Java File Code Line Count:$javaFileCodeLineCount
            
            ==================================================
            
            Kotlin File Count:$kotlinFileCount
            Kotlin File Line Count:$kotlinFileLineCount
            Kotlin File Empty Line Count:$kotlinFileEmptyLineCount
            Kotlin File Note Line Count:$kotlinFileNoteLineCount
            Kotlin File Code Line Count:$kotlinFileCodeLineCount
            
            ==================================================
            
            XML File Count:$xmlFileCount
            XML File Line Count:$xmlFileLineCount
            XML File Empty Line Count:$xmlFileEmptyLineCount
            XML File Note Line Count:$xmlFileNoteLineCount
            XML File Code Line Count:$xmlFileCodeLineCount
        """.trimIndent())
    }


    private fun analyse(rootFile: File) {

        if (rootFile.isDirectory) {
            analyseDir(rootFile)
        } else {
            analyseFile(rootFile)
        }

        println("""
            Directory:${rootFile.absolutePath}

            Java File Count:$javaFileCount
            Java File Line Count:$javaFileLineCount
            Java File Empty Line Count:$javaFileEmptyLineCount
            Java File Note Line Count:$javaFileNoteLineCount
            Java File Code Line Count:$javaFileCodeLineCount
            
            ==================================================
            
            Kotlin File Count:$kotlinFileCount
            Kotlin File Line Count:$kotlinFileLineCount
            Kotlin File Empty Line Count:$kotlinFileEmptyLineCount
            Kotlin File Note Line Count:$kotlinFileNoteLineCount
            Kotlin File Code Line Count:$kotlinFileCodeLineCount
            
            ==================================================
            
            XML File Count:$xmlFileCount
            XML File Line Count:$xmlFileLineCount
            XML File Empty Line Count:$xmlFileEmptyLineCount
            XML File Note Line Count:$xmlFileNoteLineCount
            XML File Code Line Count:$xmlFileCodeLineCount
            
        """.trimIndent())
    }

    private fun analyseDir(dir: File) {
        if (dir.isFile) return

        val files = dir.listFiles()

        for (file in files) {
            if (file.isFile)
                analyseFile(file)
            else if (file.isDirectory)
                analyseDir(file)
        }
    }

    private fun analyseFile(file: File) {
        if (file.isDirectory) return

        if (file.name.endsWith(".java")) {
            javaFileCount++
            val lines = file.readLines()
            javaFileLineCount += lines.size

            var isNoteLine = false

            for (line in lines) {

                if (line.trim().isEmpty()) {
                    javaFileEmptyLineCount++
                } else if (line.trim().startsWith("//") || line.trim().startsWith("*") || line.trim().startsWith("/*")) {
                    javaFileNoteLineCount++
                    if (line.trim().startsWith("/*") && !line.trim().endsWith("*/")) {
                        isNoteLine = true
                    }
                } else {
                    if (isNoteLine) {
                        javaFileNoteLineCount++
                    } else {
                        javaFileCodeLineCount++
                    }
                }
                if (line.trim().endsWith("*/")) {
                    isNoteLine = false
                }
            }

        } else if (file.name.endsWith(".kt")) {
            kotlinFileCount++
            val lines = file.readLines()
            kotlinFileLineCount += lines.size

            var isNoteLine = false

            for (line in lines) {

                if (line.trim().isEmpty()) {
                    kotlinFileEmptyLineCount++
                } else if (line.trim().startsWith("//") || line.trim().startsWith("*") || line.trim().startsWith("/*")) {
                    kotlinFileNoteLineCount++
                    if (line.trim().startsWith("/*") && !line.trim().endsWith("*/")) {
                        isNoteLine = true
                    }
                } else {
                    if (isNoteLine) {
                        kotlinFileNoteLineCount++
                    } else {
                        kotlinFileCodeLineCount++
                    }
                }
                if (line.trim().endsWith("*/")) {
                    isNoteLine = false
                }
            }

        } else if (file.name.endsWith(".xml")) {
            xmlFileCount++
            val lines = file.readLines()
            xmlFileLineCount += lines.size

            var isNoteLine = false

            for (line in lines) {

                if (line.trim().isEmpty()) {
                    xmlFileEmptyLineCount++
                } else if (line.trim().startsWith("<!--")) {
                    xmlFileNoteLineCount++
                    if (line.trim().startsWith("<!--") && !line.trim().endsWith("-->")) {
                        isNoteLine = true
                    }
                } else {
                    if (isNoteLine) {
                        xmlFileNoteLineCount++
                    } else {
                        xmlFileCodeLineCount++
                    }
                }
                if (line.trim().endsWith("-->")) {
                    isNoteLine = false
                }
            }
        }
    }
}