# **AND101 Pod 20 Capstone Project**

## Table of Contents

1. [App Overview](#App-Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
1. [Build Notes](#Build-Notes)

## App Overview

### Description

This app will display images, given a prompt, and will fetch images from Unsplash related to that prompt.

### App Evaluation

- **Category:** Photography
- **Mobile:** Optimized for mobile but can also work on web
- **Story:** User can look through different photo options from Unsplash, based on a search prompt, and can download any desired photos
- **Market:** Any photography enthusiasts or content creators seeking high quality stock images
- **Habit:** Not much of a focus as interaction is at the discretion of the user
- **Scope:** V1 achieves its intended purpose. V2 allows for more interaction with the photos. V3 can allow users to sign in and post their photos to Unsplash.

## Product Spec

### 1. User Features (Required and Optional)

**Basic Requirements:**

- The app calls the Unsplash successfully
- The app uses a RecyclerView
- The app uses consistent styling
- The app uses multiple user actions

**Required Features:**
- [ ] User can view a list of images based on a search prompt
- [ ] User can view details of the image, such as author, date the photo taken
- [ ] User can download a particular image to their device

**Stretch Features:**
- [ ] User can infinitely scroll through the list, which fetches more photos
- [ ] User can be redirected to the Unsplash website for a particular image
- [ ] User can switch between light and dark mode, based on the device's default setting


### 2. Chosen API(s)
Unsplash API - [[Documentation Link](https://unsplash.com/documentation#get-a-photo)]

- `GET /search/photos`
    - User can view a list of images based on a search prompt
    - User can view details of the image, such as author, date the photo taken
- `GET /photos/:id`
    - User can download a particular image to their device

### 3. User Interaction

**Required Features**

- **Get images on query**: User can type a prompt into the search
    - Feature: User can view a list of images based on a search prompt
        - Result: displays images in a RecyclerView
- **Get images on query**: User can fetch the images
    - Feature: User can view a list of images based on a search prompt
        - Result: The search bar text is attached to the HTTP request to call the Unsplash API
- **Download image** User can download a particular image listed in the Recycler View
    - Feature: User can download a particular image to their device
        - Result: A modal view will appear confirming the user to download the image or not

## Wireframes

### Lo-Fi Sketch
<!-- Add picture of your hand sketched wireframes in this section -->
<img src="https://i.imgur.com/bZSVegx.png" width=600 alt="lo-fi sketch of app">

### [BONUS] Digital Wireframes & Mockups

### [BONUS] Interactive Prototype

## Build Notes

Here's a place for any other notes on the app, it's creation
process, or what you learned this unit!

For Milestone 2, include **2+ Videos/GIFs** of the build process here!

## License

Copyright **2024** **Spring AND101 Pod 20**

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
