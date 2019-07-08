using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class FireballController : MonoBehaviour {

    int count = 0;
    public AudioClip sLaunch;
    PlayerController playerController;
    AudioSource myAudio;
    public GameObject explosionPrefab;

    // Use this for initialization
    void Start () {
        myAudio = GetComponent<AudioSource>();
        playerController = GameObject.Find("Player").GetComponent<PlayerController>();
        myAudio.PlayOneShot(sLaunch);
    }
	
	// Update is called once per frame
	void Update () {
		
	}

    private void OnCollisionEnter2D(Collision2D collision)
    {
        count++;
        if (count > 1)
        {
            Instantiate(explosionPrefab, transform.position, transform.rotation);
            Destroy(gameObject);
        }
        if (collision.gameObject.name == "Player")
        {
            switch (collision.gameObject.GetComponent<PlayerController>().PlayerType)
            {
                case E_PlayerType.Mage:
                    collision.gameObject.GetComponent<PlayerController>().MageHealth -= 5;
                    break;
                case E_PlayerType.Warrior:
                    collision.gameObject.GetComponent<PlayerController>().WarriorHealth -= 5;
                    break;
            }

        }
            
    }
}
