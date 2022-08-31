#@namespace scala di.labs.domain.thrift

struct TUserDetail{
    1:string id
    2:string username
    3:i32   age
    4:string sex
    5:string dob
}

struct TResult {
    1: i64 code
    2: list<TUserDetail> data
    3: string msg
}