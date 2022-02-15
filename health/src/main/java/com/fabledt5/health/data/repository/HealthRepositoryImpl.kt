package com.fabledt5.health.data.repository

import com.fabledt5.health.domain.model.HealthItem
import com.fabledt5.health.domain.model.Resource
import com.fabledt5.health.domain.repository.HealthRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.components.SingletonComponent
import it.czerwinski.android.hilt.annotations.BoundTo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@ExperimentalCoroutinesApi
@BoundTo(supertype = HealthRepository::class, component = SingletonComponent::class)
class HealthRepositoryImpl @Inject constructor(private val firebaseFirestore: FirebaseFirestore) :
    HealthRepository {

    companion object {
        const val HEALTH_COLLECTION = "health_collection"

        const val DATE_ADDED_FIELD = "dateAdded"
        const val TIME_ADDED_FIELD = "timeAdded"
        const val LOW_PRESSURE_FIELD = "lowPressure"
        const val HIGH_PRESSURE_FIELD = "highPressure"
        const val PULSE_FIELD = "pulse"

        const val DATE_FORMAT = "dd MMMM"
        const val TIME_FORMAT = "hh:mm"
    }

    override fun subscribeToData() = callbackFlow {
        firebaseFirestore.collection(HEALTH_COLLECTION).addSnapshotListener { values, e ->
            if (e != null) {
                trySend(Resource.Error(e))
                return@addSnapshotListener
            }
            val healthData = arrayListOf<HealthItem>()
            for (doc in values!!) {
                val healthItem = HealthItem(
                    dateAdded = doc.getString(DATE_ADDED_FIELD) ?: "",
                    timeAdded = doc.getString(TIME_ADDED_FIELD) ?: "",
                    lowPressure = doc.getString(LOW_PRESSURE_FIELD) ?: "",
                    highPressure = doc.getString(HIGH_PRESSURE_FIELD) ?: "",
                    pulse = doc.getString(PULSE_FIELD) ?: ""
                )
                healthData.add(healthItem)
            }
            trySend(Resource.Success(healthData))
        }

        awaitClose()
    }

    override fun saveData(lowPressure: String, highPressure: String, pulse: String) {
        val dateAdded = SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(Date())
        val timeAdded = SimpleDateFormat(TIME_FORMAT, Locale.getDefault()).format(Date())

        val healthData = hashMapOf(
            DATE_ADDED_FIELD to dateAdded,
            TIME_ADDED_FIELD to timeAdded,
            LOW_PRESSURE_FIELD to lowPressure,
            HIGH_PRESSURE_FIELD to highPressure,
            PULSE_FIELD to pulse
        )

        firebaseFirestore.collection(HEALTH_COLLECTION)
            .document("$dateAdded $timeAdded")
            .set(healthData)
    }

    override fun deleteData(dataAddedTime: String) {
        firebaseFirestore.collection(HEALTH_COLLECTION).document(dataAddedTime).delete()
    }

}