package com.example.coinapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.coinapp.model.Coin
import com.example.coinapp.model.User
import com.example.coinapp.model.UserCoinDataJoin

@Dao
interface UserCoinJoinDao {
    @Insert
    suspend fun insert(userCoinJoin: UserCoinDataJoin)

    @Query("DELETE FROM user_coin_join WHERE user_coin_join.userId=:userId AND user_coin_join.coinId=:coinId")
    suspend fun deleteCoinForUser(userId: Long, coinId: String)

    @Query("SELECT * FROM user INNER JOIN user_coin_join ON user.id=user_coin_join.userId WHERE user_coin_join.coinId=:coinId")
    suspend fun getUsersForCoin(coinId: String): List<User>

    @Query("SELECT * FROM coin INNER JOIN user_coin_join ON coin.id = user_coin_join.coinId WHERE user_coin_join.userId =:userId")
    suspend fun getCoinsForUser(userId: Long): List<Coin>
}