package com.stdev.shopit.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.stdev.shopit.data.model.CartItem2
import com.stdev.shopit.data.model.ShopItem

@Database(entities = [CartItem2::class, ShopItem::class], version = 3, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ShopDatabase : RoomDatabase(){

    abstract fun shopDao() : ShopDAO

    companion object{
        private var Instance:ShopDatabase?=null
        fun getDatabase(context: Context): ShopDatabase? { // context means kis activity ya fragment
            // ka sambadh hai yaha pr  pass krege = application Context jaha pr use krege.
            if (Instance==null){
                synchronized(this){
                    Instance = Room.databaseBuilder(context,ShopDatabase::class.java,"shop database")
                        .createFromAsset("shops").build()



                }  // ye method return krega ShopDatabase ka object hume.

            }
            return Instance
        }
    }

}