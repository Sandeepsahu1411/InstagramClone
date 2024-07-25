package com.example.instagramclone.models

class Reels {
    var reelsUri: String = ""
    var caption: String = ""
    var profileLink: String? = null


    constructor()
    constructor(reelsUri: String, caption: String) {
        this.reelsUri = reelsUri
        this.caption = caption
    }

    constructor(reelsUri: String, caption: String, profileLink: String) {
        this.reelsUri = reelsUri
        this.caption = caption
        this.profileLink = profileLink
    }
}