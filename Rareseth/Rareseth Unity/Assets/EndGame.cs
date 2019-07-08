using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using TMPro;

public class EndGame : MonoBehaviour {

    private void OnTriggerEnter2D(Collider2D collision)
    {
        if (collision.name == "Player")
        {
            GameObject.Find("Victory Text").GetComponent<TextMeshProUGUI>().enabled = true;
            Time.timeScale = 0;
        }
    }
}
