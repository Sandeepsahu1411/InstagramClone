package com.example.instagramclone.models

class Post {
    var postUri: String=""
    var caption:String=""
    var Uid:String=""
    var time:String=""

    constructor()
    constructor(postUri: String, caption: String) {
        this.postUri = postUri
        this.caption = caption
    }

    constructor(postUri: String, caption: String, Uid: String, time: String) {
        this.postUri = postUri
        this.caption = caption
        this.Uid = Uid
        this.time = time
    }


}