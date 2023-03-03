package com.metehanbolat.blockstore

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.blockstore.Blockstore
import com.google.android.gms.auth.blockstore.BlockstoreClient
import com.google.android.gms.auth.blockstore.RetrieveBytesRequest
import com.google.android.gms.auth.blockstore.StoreBytesData
import com.metehanbolat.blockstore.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val client: BlockstoreClient = Blockstore.getClient(this)
        val bytes1 = byteArrayOf(1, 2, 3, 4)
        val key1 = "com.metehanbolat.blockstore.key1"

        val storeRequest1: StoreBytesData = StoreBytesData.Builder()
            .setBytes(bytes1)
            .setKey(key1)
            .build()

        client.storeBytes(storeRequest1)
            .addOnSuccessListener {
                println("stored $it bytes")
            }
            .addOnFailureListener {
                println("Failed to store bytes $it")
            }

        val client2 = Blockstore.getClient(this)
        val key21 = "com.metehanbolat.blockstore.key21"
        val key22 = "com.metehanbolat.blockstore.key22"
        val key23 = BlockstoreClient.DEFAULT_BYTES_DATA_KEY

        val requestedKeys = listOf(key21, key22, key23)
        val retrieveRequest = RetrieveBytesRequest.Builder()
            .setKeys(requestedKeys)
            .build()

        client2.retrieveBytes(retrieveRequest)
            .addOnSuccessListener {
                val blockStoreDataMap = it.blockstoreDataMap
                for ((key, value) in blockStoreDataMap) {
                    println("Retrieved bytes ${value.bytes} associated with key $key")
                }
            }
            .addOnFailureListener {
                println("Failed to store bytes : $it")
            }
    }
}