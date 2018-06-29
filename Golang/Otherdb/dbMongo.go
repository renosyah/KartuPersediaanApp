package main

import "gopkg.in/mgo.v2"
import (
	"fmt"
	"gopkg.in/mgo.v2/bson"
)

func connect() *mgo.Session {
	var session, err = mgo.Dial("localhost")
	if err != nil {
		fmt.Println("gagal terhubung ke database")
	}

	return session
}

func (userData *UserData) RemoveOneDataFromCloud(sr *RestfullServer) error {
	session := connect()
	defer session.Close()

	var collection = session.DB(sr.Db.NameDB).C("DataFromCloud")
	var find = bson.M{"user.iduser":userData.IdUser}

	err := collection.Remove(find)
	if err != nil {
		return err
	}

	return nil
}

func (data *DataFromCloud) SaveDataFromCloud(sr *RestfullServer) error {
	session := connect()
	defer session.Close()

	var collection = session.DB(sr.Db.NameDB).C("DataFromCloud")

	data.User.RemoveOneDataFromCloud(sr)

	err := collection.Insert(data)
	if err != nil {
		return err
	}

	return nil
}

func (userData *UserData) LoadDataFromCloud(sr *RestfullServer) (*DataFromCloud,error) {
	session := connect()
	defer session.Close()

	var collection = session.DB(sr.Db.NameDB).C("DataFromCloud")
	var find = bson.M{"user.username":userData.UserName,"user.password":userData.Password}

	var dataFromCloud *DataFromCloud

	err := collection.Find(find).One(&dataFromCloud)
	if err != nil {
		return dataFromCloud,err
	}

	return dataFromCloud,nil

}

