cmake_minimum_required(VERSION 3.10.2)

project("camera2xx")
set(LIBRARY_NAME ${PROJECT_NAME})

add_library(${LIBRARY_NAME} SHARED camera2xx.cpp)

find_library(log-lib log)
find_library(cameraLib camera2ndk)

target_link_libraries(${LIBRARY_NAME}
        ${log-lib}
        ${cameraLib}
)