using System.Collections;
using System.Collections.Generic;
using TMPro;
using UnityEngine;

public class EnemyController : MonoBehaviour {

    GameObject player;
    public GameObject fireballPrefab;
    public AudioClip sPunch;
    AudioSource myAudio;
    PlayerController playerController;

    // Use this for initialization
    void Start () {
        myAudio = GetComponent<AudioSource>();
        player = GameObject.Find("Player");
        playerController = player.GetComponent<PlayerController>();
        Attack();
        Move();
	}
	
	// Update is called once per frame
	void Update () {
        
    }

    void shootFireball()
    {
        GameObject p = Instantiate(fireballPrefab, transform.position + transform.right * -2, transform.rotation);
        Rigidbody2D body = p.GetComponent<Rigidbody2D>();
        Vector3 shotDir = player.transform.position - p.transform.position;
        shotDir.z = 0;
        body.AddForce(shotDir.normalized * 10, ForceMode2D.Impulse);

        GameObject p2 = Instantiate(fireballPrefab, transform.position + transform.right * 2, transform.rotation);
        Rigidbody2D body2 = p2.GetComponent<Rigidbody2D>();
        Vector3 shotDir2 = player.transform.position - p2.transform.position;
        shotDir2.z = 0;
        body2.AddForce(shotDir2.normalized * 10, ForceMode2D.Impulse);

        GameObject p3 = Instantiate(fireballPrefab, transform.position + transform.up * 2, transform.rotation);
        Rigidbody2D body3 = p2.GetComponent<Rigidbody2D>();
        Vector3 shotDir3 = player.transform.position - p2.transform.position;
        shotDir3.z = 0;
        body3.AddForce(shotDir3.normalized * 10, ForceMode2D.Impulse);

        GameObject p4 = Instantiate(fireballPrefab, transform.position + transform.up * -2, transform.rotation);
        Rigidbody2D body4 = p4.GetComponent<Rigidbody2D>();
        Vector3 shotDir4 = player.transform.position - p4.transform.position;
        shotDir4.z = 0;
        body4.AddForce(shotDir4.normalized * 10, ForceMode2D.Impulse);
    }
    
    void punch()
    {
        if (Vector3.Distance(player.transform.position, transform.position) < 3)
        {
            print("Player has been punched");
            Vector3 shotDir = player.transform.position - transform.position;
            player.GetComponent<Rigidbody2D>().AddForce(shotDir.normalized * 100, ForceMode2D.Impulse);
            myAudio.PlayOneShot(sPunch);
            //playerController.damage();
        }
        else
            print("Brisket missed...");
    }
    

    void Attack()
    {
        int i = Random.Range(0,10);
        if(i <9)
        {
            Invoke("shootFireball", 1);
            Invoke("shootFireball", 1.5f);
            Invoke("shootFireball", 2);
            Invoke("Attack", Random.Range(3, 6));
        }
        else
        {
            print("Brisket is winding up a punch");
            Invoke("punch", Random.Range(1, 3));
            Invoke("Attack", Random.Range(3, 7));
        }

        
    }

    void Move()
    {
            Invoke("MoveEnemy", 3f);
            Invoke("Move", 1f);
    }

    private void MoveEnemy()
    {
        GameObject[] waypointList = GameObject.FindGameObjectsWithTag("Waypoint");
        int y = Random.Range(0, waypointList.Length);
        float step = 50f * Time.deltaTime; // calculate distance to move
        this.transform.position = Vector2.MoveTowards(transform.position, waypointList[y].transform.position, step);

    }


    private void OnCollisionEnter2D(Collision2D collision)
    {
        if(collision.gameObject.name == "Player")
        {
            print("Brisket is winding up a punch");
            Invoke("punch", 3);
        }
    }
    
}
