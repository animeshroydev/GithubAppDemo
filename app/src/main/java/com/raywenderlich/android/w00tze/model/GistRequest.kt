package com.raywenderlich.android.w00tze.model

class GistRequest(val description: String, val files: Map<String, GistFile>, val public: Boolean = true)